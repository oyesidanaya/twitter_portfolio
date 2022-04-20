SET MODE MYSQL;
DROP TABLE IF EXISTS `portfolio`;
CREATE TABLE `portfolio`
(
    `idportfolio`       int(11) NOT NULL,
    `description`       varchar(255) DEFAULT NULL,
    `image_url`         varchar(255) DEFAULT NULL,
    `twitter_user_name` varchar(255) DEFAULT NULL,
    `title`             varchar(255) DEFAULT NULL,
    PRIMARY KEY (`idportfolio`)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

INSERT INTO `portfolio` (`idportfolio`, `description`, `image_url`, `twitter_user_name`, `title`)
VALUES
(1, 'HBO Game of Thrones series 2', 'https://pbs.twimg.com/profile_images/1108745277941387264/SYLsEwkd_400x400.png', 'GameOfThrones', 'Game of Thrones');