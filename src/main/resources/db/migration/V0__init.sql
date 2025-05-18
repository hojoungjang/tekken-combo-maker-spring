CREATE DATABASE IF NOT EXISTS `tekken`;
USE `tekken`;

create table `character` (
    `id` bigint not null auto_increment,
    `created_at` datetime(6),
    `updated_at` datetime(6),
    `description` varchar(255),
    `full_name` varchar(255),
    `label` varchar(255),
    `name` varchar(255) not null,
    primary key (`id`)
) engine=InnoDB;

create table `combo` (
    `id` bigint not null auto_increment,
    `created_at` datetime(6),
    `updated_at` datetime(6),
    `damage` integer not null,
    `hit_count` integer not null,
    `name` varchar(255),
    `character_id` bigint not null,
    `post_id` bigint,
    primary key (`id`)
) engine=InnoDB;

create table `combo_move` (
    `id` bigint not null auto_increment,
    `created_at` datetime(6),
    `updated_at` datetime(6),
    `move_id` varchar(255) not null,
    `combo_id` bigint not null,
    primary key (`id`)
) engine=InnoDB;

create table `comment` (
    `id` bigint not null auto_increment,
    `created_at` datetime(6),
    `updated_at` datetime(6),
    `content` tinytext not null,
    `member_id` bigint not null,
    `post_id` bigint not null,
    `thread_id` bigint,
    primary key (`id`)
) engine=InnoDB;

create table `member` (
    `id` bigint not null auto_increment,
    `created_at` datetime(6),
    `updated_at` datetime(6),
    `email` varchar(255) not null,
    `nickname` varchar(255),
    `oauth_provider` varchar(255),
    `oauth_provider_id` varchar(255),
    `password` varchar(255),
    primary key (`id`)
) engine=InnoDB;

create table `post` (
    `id` bigint not null auto_increment,
    `created_at` datetime(6),
    `updated_at` datetime(6),
    `content` longtext,
    `title` varchar(255),
    `member_id` bigint not null,
    primary key (`id`)
) engine=InnoDB;

create table `stance` (
    `id` bigint not null auto_increment,
    `created_at` datetime(6),
    `updated_at` datetime(6),
    `name` varchar(255),
    `character_id` bigint not null,
    primary key (`id`)
) engine=InnoDB;

alter table `member`
    add constraint `UKlpau106dqhdvc2gmmc6uudii3` unique (`email`);

alter table `member`
   add constraint `UK8w4pv5jtmlaoq18n1lf7vxebw` unique (`nickname`);

alter table `combo`
   add constraint `FKo2ddrrx24a41m7itofemkcus5`
   foreign key (`character_id`)
   references `character` (`id`);

alter table `combo`
   add constraint `FKosqo5658qc63hhqlxftacqasa`
   foreign key (`post_id`)
   references `post` (`id`);

alter table `combo_move`
   add constraint `FKlc1a7w7io89lke417v9thpfyo`
   foreign key (`combo_id`)
   references `combo` (`id`);

alter table `comment`
   add constraint `FK9m00yhtfbv8dt24vxjytrbfjn`
   foreign key (`member_id`)
   references `member` (`id`);

alter table `comment`
   add constraint `FKgxbwgh8hcc6k5f2q9vkmjvdps`
   foreign key (`post_id`)
   references `post` (`id`);

alter table `comment`
   add constraint `FKlgjjq3qms6o3t549b6ej3m7tu`
   foreign key (`thread_id`)
   references `comment` (`id`);

alter table `post`
   add constraint `FK8wj1vj9i1u8j63t0syqr5au3d`
   foreign key (`member_id`)
   references `member` (`id`);

alter table `stance`
   add constraint `FKn03e9xm2t0icidxmuom21fgt2`
   foreign key (`character_id`)
   references `character` (`id`);
