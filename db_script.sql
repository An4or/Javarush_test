DROP DATABASE IF EXISTS test;
CREATE DATABASE test;
USE test;

CREATE TABLE part (
id INT(11) NOT NULL AUTO_INCREMENT,
title VARCHAR(255) NOT NULL,
is_necessity TINYINT(1) NOT NULL DEFAULT FALSE,
quantity INT(11) NOT NULL,
PRIMARY KEY (id))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

INSERT INTO test.part (title, is_necessity, quantity) VALUES

('HDD',                              1, 3),
('SSD',                              0, 5),
('процессор',                        1, 2),
('система охлаждения компьютера',    0, 7),
('система охлаждения процессора',    1, 1),
('система охлаждения корпуса',       0, 1),
('система охлаждения HDD',           0, 10),
('материнская плата',                1, 3),
('оперативная память',               1, 5),
('видеокарта',                       1, 2),
('блок питания',                     1, 18),
('корпус',                           1, 9),
('звуковая карта',   				 0, 11),
('отп. привод CD/DVD',		         1, 6),
('Blue - Ray',                       0, 2),
('USD - адаптер',                    1, 4),
('PCI - адаптер',                    0, 5),
('Wi - Fi - адаптер',                0, 2),
('Ethernet - адаптер',               1, 7),
('Подсветка корпуса',                0, 1),
('ТВ - тюнер',                       0, 3),
('FM - тюнер',                       0, 2),
('Карта видеозахвата',               0, 1),
('Bluetooth - адаптер',              0, 11),
('Картридер',                        0, 6);