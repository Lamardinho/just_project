package com.example.just_project.project_exchangerate.services;

import com.example.just_project.common.services.ContentService;
import com.example.just_project.common.services.ObjectMapperService;
import com.example.just_project.common.services.XmlMapperService;
import com.example.just_project.common.util.AppException;
import com.example.just_project.project_exchangerate.dto.exchangerate.ExchangeRateDto;
import com.example.just_project.project_exchangerate.dto.exchangerate.cbr.ValCurs;
import com.example.just_project.project_exchangerate.dtomappers.ExchangeRateMapper;
import com.example.just_project.project_exchangerate.dtomappers.RateMapper;
import com.example.just_project.project_exchangerate.enums.ERate;
import com.example.just_project.project_exchangerate.enums.ESource;
import com.example.just_project.project_exchangerate.model.exchangerate.ExchangeRate;
import com.example.just_project.project_exchangerate.repositories.DataSourceRepository;
import com.example.just_project.project_exchangerate.repositories.ExchangeRateRepository;
import com.example.just_project.project_exchangerate.repositories.RateRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.just_project.project_exchangerate.util.ExchangeErrors.DATA_SOURCE_NOT_FOUND;
import static java.lang.String.format;

/**
 * Сервис для работы с рейтингами валют
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ExchangeRateDataBaseService {

    @NonNull
    private final ExchangeRateRepository exchangeRateRepository;
    @NonNull
    private final DataSourceRepository dataSourceRepository;
    @NonNull
    private final RateRepository rateRepository;
    @NonNull
    private final ObjectMapperService objMapService;
    @NonNull
    private final XmlMapperService xmlMapperService;
    @NonNull
    private final ContentService contentService;
    @NonNull
    private final RateMapper rateMapper;
    @NonNull
    private final ExchangeRateMapper exchangeRateMapper;

    @SneakyThrows
    @Transactional
    public void createOrUpdateRubleRateFromCbrXml(ESource source) {
        val valCurs = xmlMapperService.readXml(new URL(source.getUrl()), ValCurs.class);
        val dateOfRating = LocalDate.parse(valCurs.getDate(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
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
                .collect(Collectors.toList());

        rates = rateRepository.saveAll(rates);

        // если находим -> обновляем, иначе создаём новый
        var exchangeRate = exchangeRateRepository
                .findByCurrencyAndDateRating(currency, dateOfRating) //get from DB for update
                .orElse(new ExchangeRate()); //or create new

        exchangeRate = new ExchangeRate(
                exchangeRate.getId(),
                dataSourceRepository
                        .findBySource(source)
                        .orElseThrow(() -> new AppException(format(DATA_SOURCE_NOT_FOUND, source))),
                dateOfRating,
                valCursDto.getTime(),
                currency,
                rates
        );

        exchangeRateRepository.save(exchangeRate);
    }

    public List<ExchangeRateDto> getAllCurrencyRateByUsdAndEuroDtoList(Pageable pageable) {
        val exchangeRatePage = exchangeRateRepository.findAllByCurrency(ERate.RUB, pageable);
        return exchangeRatePage.getContent()
                .stream()
                .map(exchangeRateMapper::toExchangeRateDto)
                .collect(Collectors.toList());
    }
}
