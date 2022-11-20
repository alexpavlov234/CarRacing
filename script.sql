create database if not exists carracers;
use carracers;
create table if not exists car
(
    idCar         int auto_increment
        primary key,
    modelCar      varchar(45)  not null,
    brandCar      varchar(45)  not null,
    engineCar     varchar(45)  not null,
    fuelCar       varchar(45)  not null,
    horsepowerCar int          not null,
    imageCar mediumblob null
);

create table person
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
);


create table if not exists track
(
    idTrack        int auto_increment
        primary key,
    nameTrack      varchar(45)  not null,
    lengthTrack    int          not null,
    locationTrack  varchar(45)  not null,
    imageTrack mediumblob null
);

create table if not exists race
(
    idRace           int auto_increment,
    trackRace        int  not null,
    dateRace         date not null,
    lapsRace         int  not null,
    pointsRace       int  not null,
    participantsRace int  not null,
    primary key (idRace, trackRace),
    constraint fk_race_track1
        foreign key (trackRace) references track (idTrack)
);


create index fk_race_track1_idx
    on race (trackRace);

create table if not exists race_has_car_and_driver
(
    id       int auto_increment,
    idRace   int not null,
    idCar    int not null,
    idDriver int not null,
    points   int not null,
    primary key (id, idRace, idCar, idDriver),
    constraint fk_Race_has_Car_and_Driver_Car1
        foreign key (idCar) references car (idCar),
    constraint fk_Race_has_Car_and_Driver_Driver1
        foreign key (idDriver) references person (idPerson),
    constraint fk_Race_has_Car_and_Driver_Race1
        foreign key (idRace) references race (idRace)
);


create index fk_Race_has_Car_and_Driver_Car1_idx
    on race_has_car_and_driver (idCar);

create index fk_Race_has_Car_and_Driver_Driver1_idx
    on race_has_car_and_driver (idDriver);

create table if not exists user
(
    idUser        int auto_increment,
    emailUser     varchar(45) not null unique,
    passUser      varchar(45) not null,
    typeUser      varchar(45) not null,
    userHasPerson int         not null,
    primary key (idUser, userHasPerson),
    constraint fk_user_driver1
        foreign key (userHasPerson) references person (idPerson)
);

create index fk_user_driver1_idx
    on user (userHasPerson);


