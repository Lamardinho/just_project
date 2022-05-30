--=============================================================================================================
/*
create table if not exists publisher
(
    id bigint generated by default as identity primary key, city  varchar(255) not null,
    title varchar(255) not null constraint uk_6iifkhmg1bm4skkakx6ix14sj unique
);alter table publisher owner to postgres;
create table if not exists book
(
    id bigint generated by default as identity primary key,
    isbn varchar(255) not null,title varchar(255) not null,publisher_id bigint not null
        constraint fkgtvt7p649s4x80y6f4842pnfq references publisher
);alter table book owner to postgres;
insert into publisher (city, title)
values ('Москва', 'Росмэн'),
       ('Санкт-Петербург', 'Махаон');

--drop table if exists book;
insert into book (title, isbn, publisher_id)
values ('Гарри Поттер и Философский камень', 'OZN163217492', 1),
       ('Гарри Поттер и Тайная комната', '978-5-389-07781-2', 2);
*/
--=============================================  INNER JOIN  ==========================================================
select *
from book b
         join publisher p on p.id = b.publisher_id;
select p.product_name, s.company_name, p.units_in_stock
from products p
         INNER JOIN suppliers s on p.supplier_id = s.supplier_id;

select category_name, SUM(units_in_stock)
from products p
         inner join categories c on p.category_id = c.category_id
group by category_name
order by sum(units_in_stock) desc;

select category_name, sum(unit_price * units_in_stock)
from products
         INNER JOIN categories c on c.category_id = products.category_id
where discontinued <> 1
group by category_name
having sum(unit_price * units_in_stock) > 5000
order by sum(unit_price * units_in_stock) desc;

select order_id, customer_id, first_name, last_name, title
from orders
         INNER JOIN employees e on e.employee_id = orders.employee_id;

select order_date, product_name, ship_country, p.unit_price, quantity, discount
from orders
         INNER JOIN order_details od on orders.order_id = od.order_id
         INNER JOIN products p on p.product_id = od.product_id;

select contact_name,
       company_name,
       phone,
       first_name,
       last_name,
       title,
       order_date,
       product_name,
       ship_country,
       p.unit_price,
       quantity,
       discount
from orders ord
         join order_details od on ord.order_id = od.order_id
         join products p on od.product_id = p.product_id
         join customers c on ord.customer_id = c.customer_id
         join employees e on ord.employee_id = e.employee_id
where ship_country = 'USA';

--========================================  LEFT JOIN / RIGHT JOIN  =====================================================
select company_name, order_id
from customers
         JOIN orders o on customers.customer_id = o.customer_id
where order_id is null; --для примера, ниже юзаем LEFT JOIN, без LEFT он сопоставляет записи, а с LEFT по другому
select company_name, order_id
from customers
         LEFT JOIN orders o on customers.customer_id = o.customer_id
where order_id is null;

select company_name, order_id
from orders
         RIGHT JOIN customers c on orders.customer_id = c.customer_id
where order_id is null;

select last_name, order_id
from employees
         LEFT JOIN orders o on employees.employee_id = o.employee_id
where order_id is null;
--=============================================  SELF JOIN  ==========================================================
/*
create table employee_self
(
    id         serial primary key,
    f_name     varchar(50) not null,
    l_name     varchar(50) not null,
    manager_id int,
    foreign key (manager_id) references employee_self (id)
);
insert into employee_self (f_name, l_name, manager_id)
VALUES ('e1', 'e1', null), ('e2', 'e2', 1),
       ('e3', 'e3', 1), ('e4', 'e4', 2),
       ('e5', 'e5', 3), ('e6', 'e6', 3),
       ('e7', 'e7', 3), ('e8', 'e8', 3);
*/
select e.f_name || ' ' || e.l_name   as employee,
       me.f_name || ' ' || me.l_name as manager
from employee_self e
         LEFT JOIN employee_self me on me.id = e.manager_id;
-- просто JOIN если хотим увидеть только тех кто с манагерами
--=============================================  CLEAN CODE  ==========================================================
select contact_name,
       company_name,
       phone,
       first_name,
       last_name,
       title,
       order_date,
       product_name,
       ship_country,
       p.unit_price,
       quantity,
       discount
from orders ord
         join order_details using (order_id) --od on ord.order_id = od.order_id
         join products p using (product_id) --on od.product_id = p.product_id
         join customers c using (customer_id) --on ord.customer_id = c.customer_id
         join employees e using (employee_id) --on ord.employee_id = e.employee_id
where ship_country = 'USA';

--==================================  todo: NATURAL JOIN  - НЕ ИСПОЛЬЗУЙ !  ============================================
select order_id, customer_id, first_name, last_name, title
from orders
         NATURAL JOIN employees;
--=================================================  AS  ==============================================================
-- AS нельзя использовать в WHERE и в HAVING
select count(*) AS employees_count
from employees;

select category_id, sum(units_in_stock) AS units_in_stock
from products
group by category_id
order by units_in_stock desc;

select category_id, sum(unit_price * units_in_stock) AS total_price
from products
where discontinued <> 1
group by category_id
having sum(unit_price * units_in_stock) > 5000
order by total_price desc;
