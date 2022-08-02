-- DISTINCT выборка уникальных значений
select DISTINCT city from employees;            -- select city from employees;
select DISTINCT country, city from employees;   -- select country, city from employees;
select DISTINCT country, city from customers order by country;
-- count
select count(distinct country) from employees;  -- наши работники работают в 2ух странах
-- AND = когда и true и true
select * from products where unit_price > 25 AND units_in_stock > 40;
-- OR = когда любое из условий
select company_name, city from customers where city = 'Berlin' or city = 'London' or city = 'Mannheim';
select * from orders where shipped_date > '1998-04-30' AND (freight > 75 OR freight < 150) order by freight;
-- BETWEEN - между
select customer_id, freight from orders where freight >= 19.97 and freight <= 21 order by freight;
select customer_id, freight from orders where freight BETWEEN 19.97 and 21 order by freight;
select order_id, order_date from orders where order_date BETWEEN '1998-03-30' and '1998-04-03' order by order_date;
-- IN - содержит
select contact_name, country from customers where country = 'USA' or country = 'Mexico' or country = 'Germany';
select contact_name, country from customers where country IN ('USA', 'Mexico', 'Germany');
-- NOT IN - содержит
select contact_name, country from customers where country NOT IN ('USA', 'Mexico', 'Germany');
-- MIN / MAX / AVG / SUM
select min(unit_price) from products;
select max(unit_price) from products;
select avg(unit_price) from products;
select sum(unit_price) from products;
-- LIKE
select first_name from employees where first_name LIKE '%et';   -- заканчиваются на et
select first_name from employees where first_name LIKE 'An%';   -- начинаются на An
select last_name from employees where last_name LIKE '_uch%';   -- где 1ый символ любой, далее uch, а потом хоть что
select last_name from employees where last_name LIKE '__ch%';   -- где 1 и 2 символы любые, далее uch, а потом хоть что
select last_name from employees where last_name LIKE '_uchana_'; -- первый и последний любые
-- GROUP BY -- select ship_country, freight from orders where freight > 600 order by freight desc;
select ship_country, count(*) from orders where freight > 600
GROUP BY ship_country order by count(*) desc;  -- выбрали страны где вес больше 600 и сгруппировали по странам

select ship_country, sum(freight) from orders where freight > 600
GROUP BY ship_country order by sum(freight) desc;

select category_id, sum(units_in_stock) from products
GROUP BY category_id order by sum(units_in_stock) desc limit 5; -- сумма всех категорий в продаже по id
-- HAVING
select category_id, sum(unit_price * products.units_in_stock) from products
where discontinued <> 1
group by category_id
HAVING sum(unit_price * products.units_in_stock) > 5000
order by sum(unit_price * products.units_in_stock) desc;
-- UNION - находим все которые содержатся хотя бы в 1ом месте
select country from customers
UNION   -- ALL (если добавить ALL - будет с повторами)
select country from employees
order by country;
-- INTERSECT - находим те которые есть и там и там
select country from customers
INTERSECT
select country from suppliers
order by country;
-- EXCEPT - есть в первой табличке, но нет во второй
select country from customers
EXCEPT
select country from suppliers;
