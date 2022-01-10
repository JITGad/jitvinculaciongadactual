/*==============================================================*/
/* DBMS name:      PostgreSQL 9.x                               */
/* Created on:     9/1/2022 13:07:44                            */
/*==============================================================*/


drop index TBLACTIVITIESTYPE_PK;

drop table TBLACTIVITIESTYPE;

drop index TBLCOLORTYPE_PK;

drop table TBLCOLORTYPE;

drop index RGAMEIMAGE_DETIMAGE_FK;

drop index TBLDETAILSIMAGE_PK;

drop table TBLDETAILSIMAGE;

drop index RGAMETYPE_GAME_FK;

drop index RACTTYPE_GAME_FK;

drop index TBLGAME_PK;

drop table TBLGAME;

drop index RGAMEIMAGE_COLORT_FK;

drop index RGAME_GAMEIMAGE_FK;

drop index TBLGAMEIMAGE_PK;

drop table TBLGAMEIMAGE;

drop index TBLGAMETYPE_PK;

drop table TBLGAMETYPE;

drop index RQUESTIONS_ITEMSQ_FK;

drop index TBLITEMSQUESTIONS_PK;

drop table TBLITEMSQUESTIONS;

drop index RGAME_QUESTIONS_FK;

drop index TBLQUESTIONS_PK;

drop table TBLQUESTIONS;

drop index RGAME_STATGAME_FK;

drop index TBLSTATISTICSGAME_PK;

drop table TBLSTATISTICSGAME;

drop index TBLUSER_PK;

drop table TBLUSER;

/*==============================================================*/
/* Table: TBLACTIVITIESTYPE                                     */
/*==============================================================*/
create table TBLACTIVITIESTYPE (
   IDACTIVITIESTYPE     SERIAL               not null,
   NAME                 VARCHAR(50)          not null,
   IMAGE                TEXT                 not null,
   CREATIONDATE         DATE                 not null,
   UPDATEDATE           DATE                 not null,
   STATE                BOOL                 not null,
   constraint PK_TBLACTIVITIESTYPE primary key (IDACTIVITIESTYPE)
);

/*==============================================================*/
/* Index: TBLACTIVITIESTYPE_PK                                  */
/*==============================================================*/
create unique index TBLACTIVITIESTYPE_PK on TBLACTIVITIESTYPE (
IDACTIVITIESTYPE
);

/*==============================================================*/
/* Table: TBLCOLORTYPE                                          */
/*==============================================================*/
create table TBLCOLORTYPE (
   IDCOLORTYPE          SERIAL               not null,
   NAME                 VARCHAR(30)          not null,
   RGB                  VARCHAR(20)          not null,
   HTML                 VARCHAR(20)          not null,
   CREATIONDATE         DATE                 not null,
   UPDATEDATE           DATE                 not null,
   STATE                BOOL                 not null,
   constraint PK_TBLCOLORTYPE primary key (IDCOLORTYPE)
);

/*==============================================================*/
/* Index: TBLCOLORTYPE_PK                                       */
/*==============================================================*/
create unique index TBLCOLORTYPE_PK on TBLCOLORTYPE (
IDCOLORTYPE
);

/*==============================================================*/
/* Table: TBLDETAILSIMAGE                                       */
/*==============================================================*/
create table TBLDETAILSIMAGE (
   IDDETAILSGAME        SERIAL               not null,
   IDGAMEIMAGE          INT4                 not null,
   CLIPPING_TYPE_       INT4                 not null,
   IMAGE                TEXT                 not null,
   CREATIONDATE         DATE                 not null,
   UPDATEDATE           DATE                 not null,
   STATE                BOOL                 not null,
   constraint PK_TBLDETAILSIMAGE primary key (IDDETAILSGAME)
);

/*==============================================================*/
/* Index: TBLDETAILSIMAGE_PK                                    */
/*==============================================================*/
create unique index TBLDETAILSIMAGE_PK on TBLDETAILSIMAGE (
IDDETAILSGAME
);

/*==============================================================*/
/* Index: RGAMEIMAGE_DETIMAGE_FK                                */
/*==============================================================*/
create  index RGAMEIMAGE_DETIMAGE_FK on TBLDETAILSIMAGE (
IDGAMEIMAGE
);

/*==============================================================*/
/* Table: TBLGAME                                               */
/*==============================================================*/
create table TBLGAME (
   IDGAME               SERIAL               not null,
   IDACTIVITIESTYPE     INT4                 not null,
   IDGAMETYPE           INT4                 not null,
   NAME                 VARCHAR(70)          not null,
   CREATIONDATE         DATE                 not null,
   UPDATEDATE           DATE                 not null,
   STATE                BOOL                 not null,
   LEVEL                INT4                 not null,
   constraint PK_TBLGAME primary key (IDGAME)
);

/*==============================================================*/
/* Index: TBLGAME_PK                                            */
/*==============================================================*/
create unique index TBLGAME_PK on TBLGAME (
IDGAME
);

/*==============================================================*/
/* Index: RACTTYPE_GAME_FK                                      */
/*==============================================================*/
create  index RACTTYPE_GAME_FK on TBLGAME (
IDACTIVITIESTYPE
);

/*==============================================================*/
/* Index: RGAMETYPE_GAME_FK                                     */
/*==============================================================*/
create  index RGAMETYPE_GAME_FK on TBLGAME (
IDGAMETYPE
);

/*==============================================================*/
/* Table: TBLGAMEIMAGE                                          */
/*==============================================================*/
create table TBLGAMEIMAGE (
   IDGAMEIMAGE          SERIAL               not null,
   IDGAME               INT4                 not null,
   IDCOLORTYPE          INT4                 not null,
   IMAGE                TEXT                 not null,
   PARAGRAPH            TEXT                 not null,
   AUDIO_PARAG          TEXT                 not null,
   VIDEO_PARAG          TEXT                 not null,
   CREATIONDATE         DATE                 not null,
   UPDATEDATE           DATE                 not null,
   STATE                BOOL                 not null,
   constraint PK_TBLGAMEIMAGE primary key (IDGAMEIMAGE)
);

/*==============================================================*/
/* Index: TBLGAMEIMAGE_PK                                       */
/*==============================================================*/
create unique index TBLGAMEIMAGE_PK on TBLGAMEIMAGE (
IDGAMEIMAGE
);

/*==============================================================*/
/* Index: RGAME_GAMEIMAGE_FK                                    */
/*==============================================================*/
create  index RGAME_GAMEIMAGE_FK on TBLGAMEIMAGE (
IDGAME
);

/*==============================================================*/
/* Index: RGAMEIMAGE_COLORT_FK                                  */
/*==============================================================*/
create  index RGAMEIMAGE_COLORT_FK on TBLGAMEIMAGE (
IDCOLORTYPE
);

/*==============================================================*/
/* Table: TBLGAMETYPE                                           */
/*==============================================================*/
create table TBLGAMETYPE (
   IDGAMETYPE           SERIAL               not null,
   NAME                 VARCHAR(70)          not null,
   IMAGE                TEXT                 not null,
   AUDIO_INSTRUCTIONS   TEXT                 not null,
   CREATIONDATE         DATE                 not null,
   UPDATEDATE           DATE                 not null,
   STATE                BOOL                 not null,
   SHORTNAME            VARCHAR(50)          not null,
   constraint PK_TBLGAMETYPE primary key (IDGAMETYPE)
);

/*==============================================================*/
/* Index: TBLGAMETYPE_PK                                        */
/*==============================================================*/
create unique index TBLGAMETYPE_PK on TBLGAMETYPE (
IDGAMETYPE
);

/*==============================================================*/
/* Table: TBLITEMSQUESTIONS                                     */
/*==============================================================*/
create table TBLITEMSQUESTIONS (
   ITEMSQUESTIONS       SERIAL               not null,
   IDQUESTIONS          INT4                 null,
   NAME                 VARCHAR(200)         not null,
   CORRECT              BOOL                 not null,
   IMAGE                TEXT                 not null,
   AUDIO                TEXT                 not null,
   CREATIONDATE         DATE                 not null,
   UPDATEDATE           DATE                 not null,
   STATE                BOOL                 not null,
   constraint PK_TBLITEMSQUESTIONS primary key (ITEMSQUESTIONS)
);

/*==============================================================*/
/* Index: TBLITEMSQUESTIONS_PK                                  */
/*==============================================================*/
create unique index TBLITEMSQUESTIONS_PK on TBLITEMSQUESTIONS (
ITEMSQUESTIONS
);

/*==============================================================*/
/* Index: RQUESTIONS_ITEMSQ_FK                                  */
/*==============================================================*/
create  index RQUESTIONS_ITEMSQ_FK on TBLITEMSQUESTIONS (
IDQUESTIONS
);

/*==============================================================*/
/* Table: TBLQUESTIONS                                          */
/*==============================================================*/
create table TBLQUESTIONS (
   IDQUESTIONS          SERIAL               not null,
   IDGAME               INT4                 not null,
   NAME                 VARCHAR(200)         not null,
   CREATIONDATE         DATE                 not null,
   UPDATEDATE           DATE                 not null,
   STATE                BOOL                 not null,
   constraint PK_TBLQUESTIONS primary key (IDQUESTIONS)
);

/*==============================================================*/
/* Index: TBLQUESTIONS_PK                                       */
/*==============================================================*/
create unique index TBLQUESTIONS_PK on TBLQUESTIONS (
IDQUESTIONS
);

/*==============================================================*/
/* Index: RGAME_QUESTIONS_FK                                    */
/*==============================================================*/
create  index RGAME_QUESTIONS_FK on TBLQUESTIONS (
IDGAME
);

/*==============================================================*/
/* Table: TBLSTATISTICSGAME                                     */
/*==============================================================*/
create table TBLSTATISTICSGAME (
   IDSTATISTICSGAME     SERIAL               not null,
   IDGAME               INT4                 not null,
   GAMETIME             TIME                 not null,
   MOVEMENTS            INT4                 not null,
   SCORE                DECIMAL(5,2)         not null,
   CREATIONDATE         DATE                 not null,
   UPDATEDATE           DATE                 not null,
   STATE                BOOL                 not null,
   constraint PK_TBLSTATISTICSGAME primary key (IDSTATISTICSGAME)
);

/*==============================================================*/
/* Index: TBLSTATISTICSGAME_PK                                  */
/*==============================================================*/
create unique index TBLSTATISTICSGAME_PK on TBLSTATISTICSGAME (
IDSTATISTICSGAME
);

/*==============================================================*/
/* Index: RGAME_STATGAME_FK                                     */
/*==============================================================*/
create  index RGAME_STATGAME_FK on TBLSTATISTICSGAME (
IDGAME
);

/*==============================================================*/
/* Table: TBLUSER                                               */
/*==============================================================*/
create table TBLUSER (
   IDUSER               SERIAL               not null,
   NAMES                VARCHAR(70)          not null,
   LAST_NAME            VARCHAR(70)          not null,
   EMAIL                VARCHAR(70)          not null,
   PASSWORD             TEXT                 not null,
   IMAGE                TEXT                 not null,
   BIRTHDATE            DATE                 not null,
   ROL                  VARCHAR(70)          not null,
   CREATIONDATE         DATE                 not null,
   UPDATEDATE           DATE                 not null,
   STATE                BOOL                 not null,
   constraint PK_TBLUSER primary key (IDUSER)
);

/*==============================================================*/
/* Index: TBLUSER_PK                                            */
/*==============================================================*/
create unique index TBLUSER_PK on TBLUSER (
IDUSER
);

alter table TBLDETAILSIMAGE
   add constraint FK_TBLDETAI_RGAMEIMAG_TBLGAMEI foreign key (IDGAMEIMAGE)
      references TBLGAMEIMAGE (IDGAMEIMAGE)
      on delete restrict on update restrict;

alter table TBLGAME
   add constraint FK_TBLGAME_RACTTYPE__TBLACTIV foreign key (IDACTIVITIESTYPE)
      references TBLACTIVITIESTYPE (IDACTIVITIESTYPE)
      on delete restrict on update restrict;

alter table TBLGAME
   add constraint FK_TBLGAME_RGAMETYPE_TBLGAMET foreign key (IDGAMETYPE)
      references TBLGAMETYPE (IDGAMETYPE)
      on delete restrict on update restrict;

alter table TBLGAMEIMAGE
   add constraint FK_TBLGAMEI_RGAMEIMAG_TBLCOLOR foreign key (IDCOLORTYPE)
      references TBLCOLORTYPE (IDCOLORTYPE)
      on delete restrict on update restrict;

alter table TBLGAMEIMAGE
   add constraint FK_TBLGAMEI_RGAME_GAM_TBLGAME foreign key (IDGAME)
      references TBLGAME (IDGAME)
      on delete restrict on update restrict;

alter table TBLITEMSQUESTIONS
   add constraint FK_TBLITEMS_RQUESTION_TBLQUEST foreign key (IDQUESTIONS)
      references TBLQUESTIONS (IDQUESTIONS)
      on delete restrict on update restrict;

alter table TBLQUESTIONS
   add constraint FK_TBLQUEST_RGAME_QUE_TBLGAME foreign key (IDGAME)
      references TBLGAME (IDGAME)
      on delete restrict on update restrict;

alter table TBLSTATISTICSGAME
   add constraint FK_TBLSTATI_RGAME_STA_TBLGAME foreign key (IDGAME)
      references TBLGAME (IDGAME)
      on delete restrict on update restrict;

