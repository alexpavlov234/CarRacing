create database if not exists carracers;
use carracers;
create table if not exists car
(
    idCar         int auto_increment
        primary key,
    modelCar      varchar(45) not null,
    brandCar      varchar(45) not null,
    engineCar     varchar(45) not null,
    fuelCar       varchar(45) not null,
    horsepowerCar int         not null,
    imageCar      mediumblob  null
);

create table if not exists person
(
    idPerson          int auto_increment
        primary key,
    firstNamePerson   varchar(45) not null,
    middleNamePerson  varchar(45) null,
    lastNamePerson    varchar(45) not null,
    agePerson         int         null,
    nationalityPerson varchar(45) null,
    pointsPerson      int         null,
    carPerson         int         null,
    imagePerson       mediumblob  null,
    constraint carPerson
        foreign key (carPerson) references car (idCar)
            on delete set null
);

create table if not exists track
(
    idTrack       int auto_increment
        primary key,
    nameTrack     varchar(45) not null,
    lengthTrack   int         not null,
    locationTrack varchar(45) not null,
    imageTrack    mediumblob  null,
    constraint nameTrack
        unique (nameTrack)
);

create table if not exists race
(
    idRace           int auto_increment primary key,
    trackRace        int  null,
    dateRace         date not null,
    lapsRace         int  not null,
    pointsRace       int  not null,
    participantsRace int  not null,
    constraint fk_race_track1
        foreign key (trackRace) references track (idTrack) on delete set null
);

create index fk_race_track1_idx
    on race (trackRace);

create table if not exists race_has_car_and_driver
(
    id       int auto_increment,
    idRace   int null,
    idCar    int null,
    idDriver int null,
    points   int null,
    primary key (id),
    constraint fk_Race_has_Car_and_Driver_Car1
        foreign key (idCar) references car (idCar) on delete set null,
    constraint fk_Race_has_Car_and_Driver_Driver1
        foreign key (idDriver) references person (idPerson) on delete set null,
    constraint fk_Race_has_Car_and_Driver_Race1
        foreign key (idRace) references race (idRace) on delete set null
);

create index fk_Race_has_Car_and_Driver_Car1_idx
    on race_has_car_and_driver (idCar);

create index fk_Race_has_Car_and_Driver_Driver1_idx
    on race_has_car_and_driver (idDriver);

create table if not exists user
(
    idUser        int auto_increment,
    emailUser     varchar(45) not null,
    passUser      varchar(45) not null,
    typeUser      varchar(45) not null,
    userHasPerson int         null,
    primary key (idUser),
    constraint emailUser
        unique (emailUser),
    constraint fk_user_driver1
        foreign key (userHasPerson) references person (idPerson) on delete set null
);

create index fk_user_driver1_idx
    on user (userHasPerson);

use carracers;

-- Insert data into car table
INSERT INTO car (modelCar, brandCar, engineCar, fuelCar, horsepowerCar)
VALUES ('GT-R', 'Nissan', 'V6', 'Бензин', 565),
       ('911', 'Porsche', 'Flat 6', 'Бензин', 450),
       ('Corvette', 'Chevrolet', 'V8', 'Бензин', 495),
       ('Aventador', 'Lamborghini', 'V12', 'Бензин', 740),
       ('Model S', 'Tesla', 'Електрически', 'Електричество', 670);

-- Insert data into person table
INSERT INTO person (firstNamePerson, middleNamePerson, lastNamePerson, agePerson, nationalityPerson, pointsPerson, carPerson)
VALUES ('Николай', 'Петров', 'Димитров', 25, 'България', 32,1),
       ('Мария', 'Стоянова', 'Иванова', 30, 'България', 24,2),
       ('Георги', 'Ангелов', 'Тодоров', 27, 'България', 28,3),
       ('Анна', 'Василева', 'Димитрова', 22, 'България', 5,4),
       ('Димитър', 'Петров', 'Ангелов', 28, 'България', 0,5),
       ('Администратор','','',0,'',0,null);
-- Insert data into user table
INSERT INTO user (emailUser, passUser, typeUser, userHasPerson)
VALUES ('nikolai.dimitrov@example.com', 'password1', 'User', 1),
       ('maria.ivanova@example.com', 'password2', 'User', 2),
       ('georgi.todorov@example.com', 'password3', 'User', 3),
       ('anna.dimitrova@example.com', 'password4', 'User', 4),
       ('dimitar.angelov@example.com', 'password5', 'User', 5),
       ('admin', 'admin', 'Admin', 6);

-- Insert data into track table
INSERT INTO track (nameTrack, lengthTrack, locationTrack)
VALUES ('Circuit de Spa-Francorchamps', 7004, 'Белгия'),
       ('Circuit of the Americas', 5513, 'САЩ'),
       ('Nürburgring Nordschleife', 20832, 'Германия'),
       ('Mount Panorama Circuit', 6211, 'Австралия'),
       ('Suzuka Circuit', 5807, 'Япония');

-- Insert data into race table
INSERT INTO race (trackRace, dateRace, lapsRace, pointsRace, participantsRace)
VALUES (3, '2022-05-01', 3, 20, 5),
       (1, '2022-07-15', 5, 30, 4),
       (5, '2022-08-22', 4, 25, 3),
       (2, '2022-09-10', 6, 35, 4),
       (4, '2022-11-05', 4, 25, 5);

-- Insert data into race_has_car_and_driver table
INSERT INTO race_has_car_and_driver (idRace, idCar, idDriver, points)
VALUES (1, 1, 1, 3),
       (1, 2, 2, 7),
       (1, 3, 3, 5),
       (1, 4, 4, 5),
       (2, 2, 1, 10),
       (2, 2, 1, 10),
       (2, 1, 2, 9),
       (2, 3, 3, 6),
       (3, 3, 1, 9),
       (3, 1, 2, 8),
       (3, 2, 3, 7),
       (4, 2, 3, 10);

