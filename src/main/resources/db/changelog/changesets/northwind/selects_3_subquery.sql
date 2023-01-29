-- одинаковые запросы:
select company_name
from suppliers
where country in (select distinct country from customers)
order by company_name;

select distinct s.company_name
from suppliers s
         join customers using (country)
order by s.company_name;
--
select category_name, sum(units_in_stock)
from products
         join categories using (category_id)
group by category_name
order by sum(units_in_stock) desc
limit (select min(product_id) + 4 from products);

select product_name, units_in_stock
from products
where units_in_stock > (select avg(units_in_stock) from products)
order by units_in_stock;
--
select company_name, contact_name
from customers
where exists(select customer_id
             from orders
             where orders.customer_id = customers.customer_id
               and freight between 50 and 100);

select company_name, contact_name
from customers
where not exists(select customer_id
                 from orders
                 where orders.customer_id = customers.customer_id
                   and order_date between '1995.02.01' and '1995.02.15');

select product_name
from products
where not exists(select orders.order_id
                 from orders
                          join order_details using (order_id)
                 where order_details.product_id = products.product_id
                   and order_date between '1995.02.01' and '1995.02.15');
-- Подзапросы с квантификаторами ANY, ALL
select distinct company_name
from customers
         join orders using (customer_id)
         join order_details using (order_id)
where quantity > 40;

select distinct company_name
from customers
where customer_id = any (select customer_id
                         from orders
                                  join order_details using (order_id)
                         where quantity > 40);

select distinct product_name, quantity
from products
         join order_details od using (product_id)
where quantity > (select avg(quantity) from order_details)
order by quantity;

select distinct product_name, quantity
from products
         join order_details od using (product_id)
where quantity > all (select avg(quantity) from order_details group by product_id)
order by quantity;