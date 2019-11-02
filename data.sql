INSERT INTO Users
    (email, coin, username, passcode, profile_photo)
VALUES
    ('lerong@connect.hku.hk', 1000, user1, 123a, ),
    ('lerong@hku.hk', 2000, user2, 123b, ),
    ('leongoy@hotmail.com', 3000, user3, 123c, ),
    ('ouyang.le@husky.neu.edu', 4000, user4, 123d, ),
    ('740393794@qq.com', 5000, user5, 123e, ),
    ('', 6000, user6, 123f, ),
    ('', 7000, user7, 123g, ),
    ('', 8000, user8, 123h, ),
    ('', 9000, user9, 123i, ),
    ('', 10000, user10, 123j, );


INSERT INTO Tasks
    (email, list_name, task_name, deadline, is_finished)
VALUES
    ('lerong@connect.hku.hk', 'eat well', 'have breakfast','8:00 AM', false),
    ('lerong@connect.hku.hk', 'eat well', 'have lunch','12:00 PM', false),
    ('lerong@connect.hku.hk', 'eat well', 'have dinner','6:00 PM', false),
    ('lerong@connect.hku.hk', 'live well', 'get up','7:00 AM', false),
    ('lerong@connect.hku.hk', 'live well', 'nap','1:00 PM', false),
    ('lerong@connect.hku.hk', 'live well', 'sleep','10:00 PM', false),
    ('lerongoy@hotmail.com', 'study well', 'morning study','9:00 AM', false),
    ('lerongoy@hotmail.com', 'study well', 'afternoon study','2:00 PM', false),
    ('lerongoy@hotmail.com', 'study well', 'evening study','7:00 PM', false),

INSERT INTO Hashtags
    (tag_name)
VALUES
    ('hashtag1'),
    ('hashtag2')
    ('hashtag3')
    ('hashtag4')
    ('hashtag5')
    ('hashtag6')
    ('hashtag7')

INSERT INTO Gifts
    (gift_name)
VALUES
    ('gift1'),
    ('gift2'),
    ('gift3'),
    ('gift4'),

INSERT INTO Posts
    (post_id, is_public, title, text)
VALUES
    ('1', yes, 'Title1', 'TestingText1'),
    ('2', yes, 'Title2', 'TestingText2'),
    ('3', yes, 'Title3', 'TestingText3'),
    ('4', no, 'Title4', 'TestingText4'),
    ('5', no, 'Title5', 'TestingText5'),
