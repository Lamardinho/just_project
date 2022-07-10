package com.example.just_project.project_exchangerate.services;

import com.example.just_project.common.util.AppException;
import com.example.just_project.project_exchangerate.dto.exchangerate.ExchangeRateDto;
import com.example.just_project.project_exchangerate.dto.exchangerate.cbr.ValCurs;
import com.example.just_project.project_exchangerate.dtomappers.ExchangeRateMapper;
import com.example.just_project.project_exchangerate.dtomappers.RateMapper;
import com.example.just_project.project_exchangerate.enums.ERate;
import com.example.just_project.project_exchangerate.enums.ESource;
import com.example.just_project.project_exchangerate.model.exchangerate.DataSource;
import com.example.just_project.project_exchangerate.model.exchangerate.ExchangeRate;
import com.example.just_project.project_exchangerate.repositories.DataSourceRepository;
import com.example.just_project.project_exchangerate.repositories.ExchangeRateRepository;
import com.example.just_project.project_exchangerate.repositories.RateRepository;
import com.example.just_project.project_exchangerate.util.ExchangeErrors;
import com.example.just_project.project_exchangerate.util.ExchangeRateMessages;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.example.just_project.project_exchangerate.enums.ERate.EUR;
import static com.example.just_project.project_exchangerate.enums.ERate.USD;
import static com.example.just_project.project_exchangerate.enums.ESource.CBR_RU_DAILY_ENG_XML;
import static com.example.just_project.project_exchangerate.util.ExchangeErrors.DATA_SOURCE_NOT_FOUND;
import static java.lang.String.format;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.stream.Collectors.toList;

/**
 * Сервис для работы с рейтингами валют
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Log4j2
public class ExchangeRateDataBaseService {

    @NonNull
    private final CbrRubleRatesClient cbrRubleRatesClient;
    @NonNull
    private final ExchangeRateRepository exchangeRateRepository;
    @NonNull
    private final DataSourceRepository dataSourceRepository;
    @NonNull
    private final RateRepository rateRepository;
    @NonNull
    private final RateMapper rateMapper;
    @NonNull
    private final ExchangeRateMapper exchangeRateMapper;

    /**
     * Automatically updates today's rating.
     * <p>
     * Автоматически обновляет сегодняшний рейтинг.
     */
    @Scheduled(fixedRate = 6, timeUnit = TimeUnit.HOURS)
    @Transactional
    public void updateToday() {
        try {
            createOrUpdateRubleRateFromCbrXml(LocalDate.now());
            log.info(ExchangeRateMessages.TODAY_RATINGS_UPDATE_SUCCESSFUL);
        } catch (Exception e) {
            log.error(ExchangeErrors.FAILED_TO_UPDATE_RATING + ": " + e);
        }
    }

    /**
     * Forces updating (or creating if missing) the rating for the specified day.
     * Takes the rating from the site cbr.ru -> checks if it is in our database -> updates or creates a new one.
     * <p>
     * Принудительно обновляет (или создаёт, если отсутствует) рейтинг по указанному дню.
     * Забирает рейтинг с сайта cbr.ru -> смотрит есть ли он в нашей БД -> обновляет или создаёт новый.
     *
     * @param date - rating date to be updated (дата рейтинга, который стоит обновить)
     */
    @SneakyThrows
    @Transactional
    public void createOrUpdateRubleRateFromCbrXml(LocalDate date) {
        var source = CBR_RU_DAILY_ENG_XML;
        val valCurs = cbrRubleRatesClient.getRubleRateJsonFromCbrUrlXml(
                URI.create(source.getUrl() + date.format(ofPattern("dd/MM/yyyy")))
        );
        val dateOfRating = LocalDate.parse(valCurs.getDate(), ofPattern("dd.MM.yyyy"));
        save(ERate.RUB, valCurs, source, dateOfRating);
    }

    /**
     * We do not need several ratings of one day.
     * In order not to create new ratings of one day, we simply update the latter, otherwise we create a new one.
     * <p>
     * Нам не надо несколько рейтингов одного дня.
     * Чтобы не создавать новые рейтинги одного дня, то просто обновляем последний, иначе создаём новый.
     *
     * @param currency     - currency on which the Reetings will be formed / валюта по которой будут формироваться рейтинги
     * @param valCursDto   - DTO with ratings data / DTO с данными о рейтингах
     * @param source       - data source / источник данных
     * @param dateOfRating - the date of the ratings / дата рейтингов
     */
    @Transactional
    public void save(
            @NonNull ERate currency,
            @NonNull ValCurs valCursDto,
            @NonNull ESource source,
            @NonNull LocalDate dateOfRating
    ) {
        var rates = valCursDto.getCurrencies()
                .stream()
                .map(rateMapper::toEntity)
                .collect(toList());

        rates = rateRepository.saveAll(rates);

        // если находим -> обновляем, иначе создаём новый
        var exchangeRate = exchangeRateRepository
                .findByCurrencyAndDateRating(currency, dateOfRating) //get from DB for update
                .orElse(new ExchangeRate()); //or create new

        exchangeRate = new ExchangeRate(
                exchangeRate.getId(),
                findDataSourceBySource(source),
                dateOfRating,
                valCursDto.getTime(),
                currency,
                rates
        );

        exchangeRateRepository.save(exchangeRate);
    }

    /**
     * Кешированный метод для {@link #findAllExchangeRateDtoListAndFilterByUsdAndEur(ERate, ESource, Pageable)}
     */
    @Cacheable(cacheNames = "findAllExchangeRateDtoListAndFilterByUsdAndEurCacheable")
    public List<ExchangeRateDto> findAllExchangeRateDtoListAndFilterByUsdAndEurCacheable(
            @NonNull ERate currency,
            @NonNull ESource source,
            @NonNull Pageable pageable
    ) {
        return findAllExchangeRateDtoListAndFilterByUsdAndEur(currency, source, pageable);
    }

    /**
     * Кешированный метод для {@link #findLast30ExchangeRateDtoListAndFilterByUsdAndEur(ERate, ESource, boolean)}
     */
    @Cacheable(cacheNames = "findLast30ExchangeRateDtoListAndFilterByUsdAndEurCacheable")
    public List<ExchangeRateDto> findLast30ExchangeRateDtoListAndFilterByUsdAndEurCacheable(
            @NonNull ERate currency,
            @NonNull ESource source,
            boolean sortDesc
    ) {
        return findLast30ExchangeRateDtoListAndFilterByUsdAndEur(currency, source, sortDesc);
    }

    /**
     * Находим рейтинги и фильтруем по USD и EURO
     */
    public List<ExchangeRateDto> findAllExchangeRateDtoListAndFilterByUsdAndEur(
            @NonNull ERate currency,
            @NonNull ESource source,
            @NonNull Pageable pageable
    ) {
        val exchangeRateDtoList = findAllExchangeRateDtoList(currency, source, pageable);
        filterByUsdAndEuro(exchangeRateDtoList);
        return exchangeRateDtoList;
    }

    /**
     * Находим рейтинги за последние 30 дней и фильтруем по USD и EURO
     */
    public List<ExchangeRateDto> findLast30ExchangeRateDtoListAndFilterByUsdAndEur(
            @NonNull ERate currency,
            @NonNull ESource source,
            boolean sortDesc
    ) {
        val sortParam = "dateRating";
        val pageable = (sortDesc)
                ? PageRequest.of(0, 30, Sort.by(sortParam).descending())
                : PageRequest.of(0, 30, Sort.by(sortParam));

        val exchangeRateDtoList = findAllExchangeRateDtoList(currency, source, pageable);
        filterByUsdAndEuro(exchangeRateDtoList);
        return exchangeRateDtoList;
    }

    /**
     * Находим список {@link ExchangeRate} по параметрам и мапим в DTO
     *
     * @param currency - валюта
     * @param source   - источник
     * @param pageable - параметры поиска
     * @return - list of {@link ExchangeRateDto}
     */
    private List<ExchangeRateDto> findAllExchangeRateDtoList(
            @NonNull ERate currency,
            @NonNull ESource source,
            @NonNull Pageable pageable
    ) {
        val exchangeRatePage = findAllExchangeRates(currency, source, pageable);
        return exchangeRatePage.getContent()
                .stream()
                .map(exchangeRateMapper::toExchangeRateDto)
                .collect(toList());
    }

    private Page<ExchangeRate> findAllExchangeRates(
            @NonNull ERate currency,
            @NonNull ESource source,
            @NonNull Pageable pageable
    ) {
        return exchangeRateRepository.findAllByCurrencyAndDataSource(
                currency,
                findDataSourceBySource(source),
                pageable
        );
    }

    private DataSource findDataSourceBySource(@NotNull ESource source) {
        return dataSourceRepository.findBySource(source)
                .orElseThrow(() -> new AppException(format(DATA_SOURCE_NOT_FOUND, source)));
    }

    /**
     * Фильтруем по {@link ERate#USD} and {@link ERate#EUR}
     */
    private void filterByUsdAndEuro(@NotNull List<ExchangeRateDto> exchangeRateDtoList) {
        exchangeRateDtoList.forEach(exchangeRate ->
                exchangeRate.setRates(
                        exchangeRate.getRates()
                                .stream()
                                .filter(rate -> rate.getCharCode() == USD || rate.getCharCode() == EUR)
                                .collect(toList()))
        );
    }
}
