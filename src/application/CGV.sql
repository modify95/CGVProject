create table staff(
num number primary key,
id varchar2(20) unique not null,
pw varchar2(100) not null,
name varchar2(10) not null,
birth date not null,
phone varchar2(11) not null,
authority number(1)
);

create table movie(
m_num number primary key,
m_title varchar2(200) not null,
m_genre varchar2(200) not null,
m_opendate date not null,
m_closeDate date not null,
m_runtime number(4) not null,
m_nation varchar2(50) not null,
m_grade varchar2(20) not null,
m_director varchar2(100) not null,
m_story varchar2(4000) not null,
m_poster varchar2(500) not null
);

create table cinema(
c_num number(2) primary key,
c_name varchar2(10) not null,
c_count number(3) not null
);

create table scheduel(
s_num number primary key,
s_starttime date not null,
s_finishtime date not null,
s_adultpay number(5) not null,
s_childpay number(5) not null,
c_num number(2) references cinema(c_num),
m_num number references movie(m_num),
s_starttime2 varchar2(20) not null,
s_finishtime2 varchar2(20) not null
);

create table ticketing(
t_num varchar2(16) primary key,
t_total number(7) not null,
t_date date not null,
t_seat varchar2(100) not null,
num number references staff(num),
m_num number references movie(m_num),
s_num number references scheduel(s_num),
t_count number(2) not null,
m_title varchar2(200) not null
);

--������ ����
create sequence staff_seq
minvalue 1
start with 1
increment by 1;

create sequence movie_seq
minvalue 1
start with 1
increment  by 1;

create sequence scheduel_seq
minvalue 1
start with 1
increment by 1;

create sequence ticketing_seq
maxvalue 99999999
minvalue 1
start with 1
increment by 1
cycle;

--�� insert
insert into staff values (0,'admin','8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918','������','1995-11-12','01027479340',1);

insert into cinema values (1,'1��',110);
insert into cinema values (2,'2��',110);
insert into cinema values (3,'3��',110);
insert into cinema values (4,'4��',110);
insert into cinema values (5,'5��',110);
insert into cinema values (6,'6��',110);
insert into cinema values (7,'7��',110);

commit;

--������ ����
drop sequence staff_seq;
drop sequence movie_seq;
drop sequence scheduel_seq;
drop sequence ticketing_seq;

--���̺� ����
drop table staff cascade constraints purge;
drop table scheduel cascade constraints PURGE;
drop table movie cascade constraints purge;
drop table cinema cascade constraints purge;
drop table ticketing cascade constraints purge;

<-- �ǽð� ������
select m.m_title, to_char(m.m_opendate,'YYYY.MM.DD'), trim(nvl(to_char(sum(t.t_total),'999,999,999,999,999'),0)), trim(nvl(to_char(sum(t.t_count),'999,999,999,999,999'),0))
from ticketing t, movie m
where t.m_title(+) = m.m_title and t.t_date(+) like to_date(sysdate,'YYYY.MM.DD') and m.m_closedate >= sysdate
group by m.m_title,m.m_opendate;

<-- �Ϻ� ��ȸ
select m.m_title, to_char(m.m_opendate,'YYYY.MM.DD'), trim(nvl(to_char(sum(t.t_total),'999,999,999,999,999'),0)), trim(nvl(to_char(sum(t.t_count),'999,999,999,999,999'),0))
from ticketing t, movie m
where t.m_title(+) = m.m_title and to_char(t.t_date(+),'YYYY.MM.DD') like '2018.08.22%' and to_char(m.m_closedate,'YYYY.MM.DD') >= '2018.08.22' and to_char(m.m_opendate,'YYYY.MM.DD') <= '2018.08.22' and t.t_date(+) <= sysdate - 1
group by m.m_title,m.m_opendate;

<--���� ��ȸ
select m.m_title, to_char(m.m_opendate,'YYYY.MM.DD'), trim(nvl(to_char(sum(t.t_total),'999,999,999,999,999'),0)), trim(nvl(to_char(sum(t.t_count),'999,999,999,999,999'),0))
from ticketing t, movie m
where t.m_title(+) = m.m_title and to_char(t.t_date(+),'YYYY.MM') like '2018.08%' and to_char(m.m_closedate,'YYYY.MM') >= '2018.07' and to_char(m.m_opendate,'YYYY.MM') <= '2018.07' and t.t_date(+) <= sysdate - 1
group by m.m_title, m.m_opendate;

<-- ������ ��ȸ
select m.m_title, to_char(m.m_opendate,'YYYY.MM.DD'), trim(nvl(to_char(sum(t.t_total),'999,999,999,999,999'),0)), trim(nvl(to_char(sum(t.t_count),'999,999,999,999,999'),0))
from ticketing t, movie m
where t.m_title(+) = m.m_title and to_char(t.t_date(+),'YYYY') like '2018%' and to_char(m.m_closedate,'YYYY') >= '2018' and to_char(m.m_opendate,'YYYY') <= '2018' and t.t_date(+) <= sysdate - 1
group by m.m_title, m.m_opendate;