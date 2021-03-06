PGDMP         6                x            parrot    11.5    12.1 F               0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    45988    parrot    DATABASE     �   CREATE DATABASE parrot WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'English_United States.1252' LC_CTYPE = 'English_United States.1252';
    DROP DATABASE parrot;
                postgres    false            �           0    0    DATABASE parrot    COMMENT     G   COMMENT ON DATABASE parrot IS 'Register and Manager User Invitations';
                   postgres    false    2946                        2615    45989    default    SCHEMA        CREATE SCHEMA "default";
    DROP SCHEMA "default";
                postgres    false            �            1259    46063    account    TABLE     ,  CREATE TABLE "default".account (
    id bigint NOT NULL,
    owner bigint NOT NULL,
    name character varying NOT NULL,
    created_on timestamp without time zone,
    updated_on timestamp without time zone,
    author_id bigint NOT NULL,
    updated_by bigint,
    external_id character varying
);
    DROP TABLE "default".account;
       default            postgres    false    6            �            1259    70564    account_id_seq    SEQUENCE     �   ALTER TABLE "default".account ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME "default".account_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            default          postgres    false    207    6            �            1259    54201    departments    TABLE     �   CREATE TABLE "default".departments (
    id bigint NOT NULL,
    name character varying NOT NULL,
    code character varying NOT NULL,
    office_id bigint NOT NULL,
    parent_department bigint
);
 "   DROP TABLE "default".departments;
       default            postgres    false    6            �            1259    54217    employee_station    TABLE     �   CREATE TABLE "default".employee_station (
    id bigint NOT NULL,
    employee_id bigint NOT NULL,
    office_id bigint NOT NULL,
    station_id bigint
);
 '   DROP TABLE "default".employee_station;
       default            postgres    false    6            �            1259    54209 	   employees    TABLE     �   CREATE TABLE "default".employees (
    id bigint NOT NULL,
    names character varying,
    gender character varying,
    created_on timestamp without time zone,
    updated_on timestamp without time zone,
    author_id bigint,
    updated_by bigint
);
     DROP TABLE "default".employees;
       default            postgres    false    6            �            1259    54180    kiosks    TABLE     K  CREATE TABLE "default".kiosks (
    id bigint NOT NULL,
    reference character varying NOT NULL,
    details character varying,
    device_token character varying,
    created_on timestamp without time zone NOT NULL,
    updated_on timestamp without time zone,
    author_id bigint,
    udated_by bigint,
    station_id bigint
);
    DROP TABLE "default".kiosks;
       default            postgres    false    6            �            1259    54196    office_station    TABLE     x   CREATE TABLE "default".office_station (
    id bigint NOT NULL,
    office_id bigint NOT NULL,
    station_id bigint
);
 %   DROP TABLE "default".office_station;
       default            postgres    false    6            �            1259    54188    offices    TABLE     &  CREATE TABLE "default".offices (
    id bigint NOT NULL,
    name character varying NOT NULL,
    dateopened date NOT NULL,
    parent_office bigint,
    created_on timestamp without time zone NOT NULL,
    updated_on timestamp without time zone,
    author_id bigint,
    updated_by bigint
);
    DROP TABLE "default".offices;
       default            postgres    false    6            �            1259    46050    permission_scope    TABLE     |   CREATE TABLE "default".permission_scope (
    id bigint NOT NULL,
    key character varying,
    value character varying
);
 '   DROP TABLE "default".permission_scope;
       default            postgres    false    6            �            1259    46027    permissions    TABLE     �   CREATE TABLE "default".permissions (
    id bigint NOT NULL,
    code character varying,
    name character varying,
    "grouping" character varying
);
 "   DROP TABLE "default".permissions;
       default            postgres    false    6            �            1259    46025    permissions_id_seq    SEQUENCE     �   ALTER TABLE "default".permissions ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME "default".permissions_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            default          postgres    false    6    204            �            1259    62373    profile    TABLE     #  CREATE TABLE "default".profile (
    id bigint NOT NULL,
    user_id bigint NOT NULL,
    firstname character varying,
    lastname character varying,
    author_id bigint,
    created_on time without time zone NOT NULL,
    updated_by bigint,
    dateupdated timestamp without time zone
);
    DROP TABLE "default".profile;
       default            postgres    false    6            �            1259    46000    role    TABLE     S  CREATE TABLE "default".role (
    id bigint NOT NULL,
    code character varying(50) NOT NULL,
    name character varying(200) NOT NULL,
    is_system boolean NOT NULL,
    created_on timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_on timestamp without time zone,
    author_id bigint,
    updated_by bigint
);
    DROP TABLE "default".role;
       default            postgres    false    6            �            1259    45998    role_id_seq    SEQUENCE     �   ALTER TABLE "default".role ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME "default".role_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            default          postgres    false    199    6            �            1259    46035    role_permission    TABLE     &  CREATE TABLE "default".role_permission (
    id bigint NOT NULL,
    role_id bigint NOT NULL,
    permission_id bigint NOT NULL,
    created_on timestamp without time zone NOT NULL,
    updated_on timestamp without time zone,
    author_id bigint,
    updated_by bigint,
    scope_id bigint
);
 &   DROP TABLE "default".role_permission;
       default            postgres    false    6            �            1259    46086    stations    TABLE     �   CREATE TABLE "default".stations (
    id bigint NOT NULL,
    account_id bigint NOT NULL,
    name character varying,
    code character varying
);
    DROP TABLE "default".stations;
       default            postgres    false    6            �            1259    46010 	   user_role    TABLE     '  CREATE TABLE "default".user_role (
    id bigint NOT NULL,
    user_id bigint NOT NULL,
    role_id bigint NOT NULL,
    status character varying(50) NOT NULL,
    author_id bigint,
    created_on timestamp without time zone,
    updated_on timestamp without time zone,
    updated_by bigint
);
     DROP TABLE "default".user_role;
       default            postgres    false    6            �            1259    46008    user_role_id_seq    SEQUENCE     �   ALTER TABLE "default".user_role ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME "default".user_role_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            default          postgres    false    6    202            �            1259    45990    users    TABLE     =  CREATE TABLE "default".users (
    id bigint NOT NULL,
    username character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    author_id bigint,
    created_on timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_on timestamp without time zone,
    updated_by bigint
);
    DROP TABLE "default".users;
       default            postgres    false    6            �           0    0    TABLE users    COMMENT     4   COMMENT ON TABLE "default".users IS 'Manage Users';
          default          postgres    false    197            �            1259    46006    users_id_seq    SEQUENCE     �   ALTER TABLE "default".users ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME "default".users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            default          postgres    false    6    197            s          0    46063    account 
   TABLE DATA           q   COPY "default".account (id, owner, name, created_on, updated_on, author_id, updated_by, external_id) FROM stdin;
    default          postgres    false    207   vU       x          0    54201    departments 
   TABLE DATA           V   COPY "default".departments (id, name, code, office_id, parent_department) FROM stdin;
    default          postgres    false    212   �U       z          0    54217    employee_station 
   TABLE DATA           U   COPY "default".employee_station (id, employee_id, office_id, station_id) FROM stdin;
    default          postgres    false    214   �U       y          0    54209 	   employees 
   TABLE DATA           h   COPY "default".employees (id, names, gender, created_on, updated_on, author_id, updated_by) FROM stdin;
    default          postgres    false    213   �U       u          0    54180    kiosks 
   TABLE DATA           �   COPY "default".kiosks (id, reference, details, device_token, created_on, updated_on, author_id, udated_by, station_id) FROM stdin;
    default          postgres    false    209   
V       w          0    54196    office_station 
   TABLE DATA           F   COPY "default".office_station (id, office_id, station_id) FROM stdin;
    default          postgres    false    211   'V       v          0    54188    offices 
   TABLE DATA           x   COPY "default".offices (id, name, dateopened, parent_office, created_on, updated_on, author_id, updated_by) FROM stdin;
    default          postgres    false    210   DV       r          0    46050    permission_scope 
   TABLE DATA           =   COPY "default".permission_scope (id, key, value) FROM stdin;
    default          postgres    false    206   aV       p          0    46027    permissions 
   TABLE DATA           D   COPY "default".permissions (id, code, name, "grouping") FROM stdin;
    default          postgres    false    204   ~V       {          0    62373    profile 
   TABLE DATA           v   COPY "default".profile (id, user_id, firstname, lastname, author_id, created_on, updated_by, dateupdated) FROM stdin;
    default          postgres    false    215   �V       k          0    46000    role 
   TABLE DATA           k   COPY "default".role (id, code, name, is_system, created_on, updated_on, author_id, updated_by) FROM stdin;
    default          postgres    false    199   �V       q          0    46035    role_permission 
   TABLE DATA           �   COPY "default".role_permission (id, role_id, permission_id, created_on, updated_on, author_id, updated_by, scope_id) FROM stdin;
    default          postgres    false    205   W       t          0    46086    stations 
   TABLE DATA           A   COPY "default".stations (id, account_id, name, code) FROM stdin;
    default          postgres    false    208   %W       n          0    46010 	   user_role 
   TABLE DATA           s   COPY "default".user_role (id, user_id, role_id, status, author_id, created_on, updated_on, updated_by) FROM stdin;
    default          postgres    false    202   BW       i          0    45990    users 
   TABLE DATA           i   COPY "default".users (id, username, password, author_id, created_on, updated_on, updated_by) FROM stdin;
    default          postgres    false    197   oW       �           0    0    account_id_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('"default".account_id_seq', 2, true);
          default          postgres    false    216            �           0    0    permissions_id_seq    SEQUENCE SET     D   SELECT pg_catalog.setval('"default".permissions_id_seq', 1, false);
          default          postgres    false    203            �           0    0    role_id_seq    SEQUENCE SET     <   SELECT pg_catalog.setval('"default".role_id_seq', 2, true);
          default          postgres    false    198            �           0    0    user_role_id_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('"default".user_role_id_seq', 4, true);
          default          postgres    false    201            �           0    0    users_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('"default".users_id_seq', 8, true);
          default          postgres    false    200            �
           2606    46070    account account_pkey 
   CONSTRAINT     U   ALTER TABLE ONLY "default".account
    ADD CONSTRAINT account_pkey PRIMARY KEY (id);
 A   ALTER TABLE ONLY "default".account DROP CONSTRAINT account_pkey;
       default            postgres    false    207            �
           2606    54208    departments departments_pkey 
   CONSTRAINT     ]   ALTER TABLE ONLY "default".departments
    ADD CONSTRAINT departments_pkey PRIMARY KEY (id);
 I   ALTER TABLE ONLY "default".departments DROP CONSTRAINT departments_pkey;
       default            postgres    false    212            �
           2606    54221 &   employee_station employee_station_pkey 
   CONSTRAINT     g   ALTER TABLE ONLY "default".employee_station
    ADD CONSTRAINT employee_station_pkey PRIMARY KEY (id);
 S   ALTER TABLE ONLY "default".employee_station DROP CONSTRAINT employee_station_pkey;
       default            postgres    false    214            �
           2606    54216    employees employees_pkey 
   CONSTRAINT     Y   ALTER TABLE ONLY "default".employees
    ADD CONSTRAINT employees_pkey PRIMARY KEY (id);
 E   ALTER TABLE ONLY "default".employees DROP CONSTRAINT employees_pkey;
       default            postgres    false    213            �
           2606    54187    kiosks kiosks_pkey 
   CONSTRAINT     S   ALTER TABLE ONLY "default".kiosks
    ADD CONSTRAINT kiosks_pkey PRIMARY KEY (id);
 ?   ALTER TABLE ONLY "default".kiosks DROP CONSTRAINT kiosks_pkey;
       default            postgres    false    209            �
           2606    54200 "   office_station office_station_pkey 
   CONSTRAINT     c   ALTER TABLE ONLY "default".office_station
    ADD CONSTRAINT office_station_pkey PRIMARY KEY (id);
 O   ALTER TABLE ONLY "default".office_station DROP CONSTRAINT office_station_pkey;
       default            postgres    false    211            �
           2606    54195    offices offices_pkey 
   CONSTRAINT     U   ALTER TABLE ONLY "default".offices
    ADD CONSTRAINT offices_pkey PRIMARY KEY (id);
 A   ALTER TABLE ONLY "default".offices DROP CONSTRAINT offices_pkey;
       default            postgres    false    210            �
           2606    46057 &   permission_scope permission_scope_pkey 
   CONSTRAINT     g   ALTER TABLE ONLY "default".permission_scope
    ADD CONSTRAINT permission_scope_pkey PRIMARY KEY (id);
 S   ALTER TABLE ONLY "default".permission_scope DROP CONSTRAINT permission_scope_pkey;
       default            postgres    false    206            �
           2606    46034    permissions permissions_pkey 
   CONSTRAINT     ]   ALTER TABLE ONLY "default".permissions
    ADD CONSTRAINT permissions_pkey PRIMARY KEY (id);
 I   ALTER TABLE ONLY "default".permissions DROP CONSTRAINT permissions_pkey;
       default            postgres    false    204            �
           2606    62380    profile profile_pkey 
   CONSTRAINT     U   ALTER TABLE ONLY "default".profile
    ADD CONSTRAINT profile_pkey PRIMARY KEY (id);
 A   ALTER TABLE ONLY "default".profile DROP CONSTRAINT profile_pkey;
       default            postgres    false    215            �
           2606    46039 $   role_permission role_permission_pkey 
   CONSTRAINT     e   ALTER TABLE ONLY "default".role_permission
    ADD CONSTRAINT role_permission_pkey PRIMARY KEY (id);
 Q   ALTER TABLE ONLY "default".role_permission DROP CONSTRAINT role_permission_pkey;
       default            postgres    false    205            �
           2606    46005    role role_pkey 
   CONSTRAINT     O   ALTER TABLE ONLY "default".role
    ADD CONSTRAINT role_pkey PRIMARY KEY (id);
 ;   ALTER TABLE ONLY "default".role DROP CONSTRAINT role_pkey;
       default            postgres    false    199            �
           2606    46093    stations stations_pkey 
   CONSTRAINT     W   ALTER TABLE ONLY "default".stations
    ADD CONSTRAINT stations_pkey PRIMARY KEY (id);
 C   ALTER TABLE ONLY "default".stations DROP CONSTRAINT stations_pkey;
       default            postgres    false    208            �
           2606    46014    user_role user_role_pkey 
   CONSTRAINT     Y   ALTER TABLE ONLY "default".user_role
    ADD CONSTRAINT user_role_pkey PRIMARY KEY (id);
 E   ALTER TABLE ONLY "default".user_role DROP CONSTRAINT user_role_pkey;
       default            postgres    false    202            �
           2606    45997    users users_pkey 
   CONSTRAINT     Q   ALTER TABLE ONLY "default".users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);
 =   ALTER TABLE ONLY "default".users DROP CONSTRAINT users_pkey;
       default            postgres    false    197            �
           2606    46076    account author_id    FK CONSTRAINT     �   ALTER TABLE ONLY "default".account
    ADD CONSTRAINT author_id FOREIGN KEY (author_id) REFERENCES "default".users(id) NOT VALID;
 >   ALTER TABLE ONLY "default".account DROP CONSTRAINT author_id;
       default          postgres    false    2763    207    197            �
           2606    46071    account owner    FK CONSTRAINT     p   ALTER TABLE ONLY "default".account
    ADD CONSTRAINT owner FOREIGN KEY (owner) REFERENCES "default".users(id);
 :   ALTER TABLE ONLY "default".account DROP CONSTRAINT owner;
       default          postgres    false    2763    207    197            �
           2606    46045    role_permission permission_id    FK CONSTRAINT     �   ALTER TABLE ONLY "default".role_permission
    ADD CONSTRAINT permission_id FOREIGN KEY (permission_id) REFERENCES "default".permissions(id) NOT VALID;
 J   ALTER TABLE ONLY "default".role_permission DROP CONSTRAINT permission_id;
       default          postgres    false    205    204    2769            �
           2606    46020    user_role role    FK CONSTRAINT     |   ALTER TABLE ONLY "default".user_role
    ADD CONSTRAINT role FOREIGN KEY (role_id) REFERENCES "default".role(id) NOT VALID;
 ;   ALTER TABLE ONLY "default".user_role DROP CONSTRAINT role;
       default          postgres    false    202    199    2765            �
           2606    46040    role_permission role_id    FK CONSTRAINT     �   ALTER TABLE ONLY "default".role_permission
    ADD CONSTRAINT role_id FOREIGN KEY (role_id) REFERENCES "default".role(id) NOT VALID;
 D   ALTER TABLE ONLY "default".role_permission DROP CONSTRAINT role_id;
       default          postgres    false    199    2765    205            �
           2606    46058    role_permission scope_id    FK CONSTRAINT     �   ALTER TABLE ONLY "default".role_permission
    ADD CONSTRAINT scope_id FOREIGN KEY (scope_id) REFERENCES "default".permission_scope(id) NOT VALID;
 E   ALTER TABLE ONLY "default".role_permission DROP CONSTRAINT scope_id;
       default          postgres    false    2773    205    206            �
           2606    46081    account updated_by    FK CONSTRAINT     �   ALTER TABLE ONLY "default".account
    ADD CONSTRAINT updated_by FOREIGN KEY (updated_by) REFERENCES "default".users(id) NOT VALID;
 ?   ALTER TABLE ONLY "default".account DROP CONSTRAINT updated_by;
       default          postgres    false    2763    197    207            �
           2606    46015    user_role user    FK CONSTRAINT        ALTER TABLE ONLY "default".user_role
    ADD CONSTRAINT "user" FOREIGN KEY (user_id) REFERENCES "default".users(id) NOT VALID;
 =   ALTER TABLE ONLY "default".user_role DROP CONSTRAINT "user";
       default          postgres    false    202    197    2763            s   -   x�3�4���OIU��K��!3\R�������`������ �|	�      x      x������ � �      z      x������ � �      y      x������ � �      u      x������ � �      w      x������ � �      v      x������ � �      r      x������ � �      p      x������ � �      {      x������ � �      k   @   x�3���s�ttv�����J8��t�u���L����M�,�L8c� �+F��� �5      q      x������ � �      t      x������ � �      n      x�3�4���̲T�?8����� m��      i   �   x���Mn�0F��)� ��x<c�zN����5%R{�&������轧OCn���y����˟���˲�Lsu���A��;����<C��k�x�'�v�b&�`J� �Q_X��AT����/�
�	�^SP�f��v�]�?\�u��B���g��H�T9W����F*��Y	+J�j�Q�m�*q��5z-�h�$�zb5�5W�[MۇY}�9�jﻮ�����     