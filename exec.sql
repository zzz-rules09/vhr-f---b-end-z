insert into menu values(32,"/","/home","Home","地图管理","fa fa-flag",null,1,1,1);
insert into menu values(33,"/map/manage/**","/map/manage","Map3d1","地图图片管理",null,null,1,32,1);
insert into menu values(34,"/map/map/3d/**","/map/map3d","Map3d","3d地图管理",null,null,1,32,1);
insert into menu_role values(284,33,6);
insert into menu_role values(285,34,6);

create table baiduImageBed (
 id int unsigned auto_increment,
 name varchar(100) not null,
 keyword varchar(150) not null,
 primary key (id)
);