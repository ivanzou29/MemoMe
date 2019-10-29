create table Users (
    primary key email varchar(50),
    coin int(),
    username varchar(20),
    passcode varchar(20),
    profile_photo varbinary()
);

create table Tasks (
    email varchar(50),
    list_name varchar(20),
    task_name varchar(20),
    deadline date,
    is_finished boolean,
    primary key (list_name, task_name, email),
    foreign key email references Users (email) on delete cascade
);

create table Posts (
    primary key post_id char(20),
    is_public boolean,
    title varchar(50),
    text varchar(1000)
);

create table Hashtags (
    primary key tag_name varchar(20)
);

create table HaveTag (
    post_id char(20),
    tag_name varchar(20),
    foreign key post_id references Posts (post_id) on delete cascade,
    foreign key tag_name references Hashtags (tag_name),
);

create table Favorite (
    email varchar(50),
    post_id char(20),
    foreign_key email references Users (email) on delete cascade,
    foreign_key post_id references Posts (post_id) on delete cascade
);

create table Compose (
    email varchar(50),
    post_id char(20),
    foreign_key email references Users (email) on delete cascade,
    foreign_key post_id references Posts (post_id) on delete cascade
);

create table Gifts (
    primary key gift_name varchar(20),
    value int()
);

create table Own (
    email varchar(50),
    gift_name varchar(20),
    quantity int(),
    foreign key email references Users (email) on delete cascade,
    foreign key gift_name references Gifts (gift_name) on delete cascade
);

create table ReceiveGifts (
    post_id char(20),
    gift_name varchar(20),
    quantity int(),
    foreign key post_id references Posts (post_id) on delete cascade,
    foreign key gift_name references Gifts (gift_name) on delete cascade
);

create table Images (
    image_id int(1),
    image_bytearray varbinary(),
    post_id char(20),
    foreign key post_id references Posts (post_id) on delete cascade,
    primary key (image_id, post_id)
);
