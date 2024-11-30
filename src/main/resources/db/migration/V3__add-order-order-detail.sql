drop table if exists beer_order;

drop table if exists beer_order_line;


create table beer_order
(
    version            bigint,
    created_date       datetime(6),
    customer_ref       varchar(255),
    last_modified_date datetime(6),
    id                 varchar(36) not null,
    customer_id        varchar(36),
    primary key (id),
    constraint foreign key (customer_id) references customer (id)
) engine=InnoDB;

create table beer_order_line
(
    version            bigint,
    beer_id            varchar(36),
    created_date       datetime(6),
    last_modified_date datetime(6),
    order_quantity     integer,
    quantity_allocated integer,
    id                 varchar(36) not null,
    beer_order_id      varchar(36),
    primary key (id),
    constraint foreign key (beer_id) references beer (id),
    constraint foreign key (beer_order_id) references beer_order (id)
) engine=InnoDB;