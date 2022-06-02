-- Выборка и элементарные арифметические операции
select product_id, product_name, (unit_price * units_in_stock) from products;
select order_id, (shipped_date - order_date) from orders;
-- DISTINCT выборка уникальных значений
select DISTINCT city from employees;            -- select city from employees;
select DISTINCT country, city from employees;   -- select country, city from employees;
select DISTINCT country, city from customers order by country;
-- count
select count(distinct country) from employees;  -- наши работники работают в 2ух странах
-- WHERE
select product_name, unit_price from products WHERE unit_price > 20 order by unit_price;
select count(*) from orders WHERE order_date > '1998-03-01';
-- AND = когда и true и true
select * from products where unit_price > 25 AND units_in_stock > 40;
-- OR = когда любое из условий
select company_name, city from customers where city = 'Berlin' or city = 'London' or city = 'Mannheim';
select * from orders where shipped_date > '1998-04-30' AND (freight > 75 OR freight < 150) order by freight;
-- BETWEEN - между
select * from orders where freight >= 20 and freight <= 21 order by freight;
select customer_id, freight from orders where freight BETWEEN 19.97 and 21 order by freight;
select order_id, order_date from orders where order_date BETWEEN '1998-03-30' and '1998-04-03' order by order_date;
-- IN - содержит
select contact_name, country from customers where country = 'USA' or country = 'Mexico' or country = 'Germany';
select contact_name, country from customers where country IN ('USA', 'Mexico', 'Germany');
-- NOT IN - содержит
select contact_name, country from customers where country NOT IN ('USA', 'Mexico', 'Germany');
-- ORDER BY
select category_name from categories ORDER BY category_name; --(default = asc)
select category_name from categories ORDER BY category_name desc;
-- MIN / MAX / AVG / SUM
select min(order_date) from orders;
select max(order_date) from orders;
select avg(unit_price) from products;
select sum(units_in_stock) from products;
-- LIKE
select first_name from employees where first_name LIKE '%et';   -- заканчиваются на et
select first_name from employees where first_name LIKE 'An%';   -- начинаются на A
select last_name from employees where last_name LIKE '_uch%';   -- где 1ый символ любой, далее uch, а потом хоть что
select last_name from employees where last_name LIKE '__ch%';   -- где 1 и 2 символы любые, далее uch, а потом хоть что
select last_name from employees where last_name LIKE '_uchana_'; -- первый и последний любые
-- LIMIT
select order_date from orders order by order_date desc LIMIT 10;
-- is null / not null
select ship_city, ship_region, ship_country from orders where ship_region IS NULL;
select ship_city, ship_region, ship_country from orders where ship_region IS NOT NULL;
-- GROUP BY -- select ship_country, freight from orders where freight > 600 order by freight desc;
select ship_country, count(*) from orders where freight > 600
GROUP BY ship_country order by count(*) desc;   -- выбрали страны где вес больше 600 и сгруппировали по странам

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
