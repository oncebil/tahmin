select sum(genyen) from ( a   select ganyan as genyen, sonucNo,gm_RegressionPredicted,ganyan, * from gm_KosuTahminNominal  , AtKosu
where AtKosu.KosuKodu = gm_KosuTahminNominal.gm_KosuId
and AtKosu.AtKodu = gm_KosuTahminNominal.gm_AtId
and gm_RegressionPredicted <0.4
--AND gm_regressionActual = 1
and gm_experiment = 'Son7Kosu-SMO-0'
--and  gm_KosuId = 47978
order by gm_KosuId,gm_RegressionPredicted
) A


select sum(genyen) from (
   select ganyan as genyen, sonucNo,gm_RegressionPredicted,ganyan, * from gm_KosuTahminNominal  , AtKosu
where AtKosu.KosuKodu = gm_KosuTahminNominal.gm_KosuId
and AtKosu.AtKodu = gm_KosuTahminNominal.gm_AtId
--and gm_yesprediction > 0.86
--AND sonucno = 1
and gm_experiment = 'Son7Kosu-SMO-0'
and  gm_KosuId = 48027
order by gm_KosuId,gm_yesprediction
) A




3.3  14.25   40
3.1   5.95   12

35  65

2.6 63



48


 9.55



17.3  27
15.95



SELECT
 column_name
FROM
 information_schema.COLUMNS
WHERE
 TABLE_NAME = 'gm_kosutahminnominal';



select *  from gm_KosuTahminNominal  where gm_experiment = 'Son7Kosu-SMO-0' ORDER BY GM_KOSUID, GM_REGRESSIONPREDICTED;

select *  from gm_KosuTahminNominal ;

select * from AtKosu;


select count(*) from Atkosu;

select * from Atkosu where kosukodu = 80047953 and Atkodu = 80022519;


SELECT * FROM AtKosu WHERE KosuKodu in (47947,48162,48163,47953,48020,48030) order by KosuKodu,AtKodu

commit;

select distinct gm_KOsuId from gm_KosuTahminNominal  where gm_experiment = 'Son7Kosu-SMO-0' and gm_prediction = 'Y' order by gm_KosuId;

delete from gm_KosuTahminNominal;

select distinct gm_KOsuId from gm_KosuTahminNominal  where gm_experiment = 'Son7Kosu-SMO-0' and gm_prediction = 'N'  order by gm_KosuId;

153 kosu var



-F "weka.filters.unsupervised.attribute.RemoveType -T string"  -W weka.classifiers.lazy.KStar  -- -B 20 -M a

3.0  63    72
2.9  54    58
2.8  47.9   49
2.7  29.5  38
2.6  29.5   34
2.5   28.1   29
2.4  25.9      23   ------- kazanirdik
2.3   23.1    21 ------- kazanirdik
2.2  20.25   16 -- kazanirdik 1.26
2.1  18.15  14 -- kazanirdik

-F "weka.filters.unsupervised.attribute.RemoveType -T string"  -W weka.classifiers.lazy.KStar --  -B 12 -M a

2.4 78.5 78 -- kazanirdik
2.3  71.1  70 -- kazanirdik
2.2  64.9 55 -- kazanirdik  1.18
2.1 50.2  46 --kazanirdik
2.0  32.55 36 kaybederdik



2.4  18.45 13 -- kazanirdik
2.5   20.65     19 -- kazanirdik
2.6  22.5 25


3.2  14 37
3.1  10   25
4.3   246    519
3.8  201  353
3.5  132  221
3.0   19.75  61
2.9 13.3  42
2.7   2.45  25
3,1 32.5 81

49
47.9

297
54.7







49
47


38
29


42






65 aldik



90 aldik


29 verdik

19 aldik





47978

182 verdik

122 kazandik

93 verdik
48 kazandik

-- 157 kosu var


   select sonucNo,gm_RegressionPredicted,ganyan, * from gm_KosuTahminNominal  , AtKosu
where AtKosu.KosuKodu = gm_KosuTahminNominal.gm_KosuId
and AtKosu.AtKodu = gm_KosuTahminNominal.gm_AtId
and AtKosu.KosuKodu in (    select gm_KosuId from gm_KosuTahminNominal where gm_RegressionActual = 0 and gm_RegressionPredicted <100 )
and gm_experiment = 'Son7Kosu-SMO-0'
order by gm_KosuId, gm_RegressionPredicted

select * from gm_KosuTahminNominal where gm_experiment = 'Son7Kosu-SMO-0'

order by gm_KosuId,gm_RegressionPredicted;




select sum(genyen) from (
select C.SonucNo, C.ganyan as genyen, A.*,C.* from gm_KosuTahminNominal A,
(select gm_KosuId,min(gm_RegressionPredicted) as minderece from gm_KosuTahminNominal where gm_experiment = 'Son7Kosu-SMO-0'
group by gm_KosuId
order by gm_KosuId ) B,
AtKosu C
where A.gm_KosuId = B.gm_KosuId
and A.gm_RegressionPredicted = B.minderece
--and B.minderece <100
and C.AtKodu = A.gm_AtId
and C.KosuKodu = A.gm_KosuId
--and A.gm_RegressionActual = 0
and C.Son7BitirisOrtalamasi < 3.60
order by A.gm_KosuId ) D

--201 kosuvar
 -- 57


-- 28  kosuda bilirdik


select * from gm_KosuTahminNominal where gm_KosuId = 48440 order by gm_RegressionPredicted;



select sonucNo,gm_RegressionPredicted, * from gm_KosuTahminNominal  , AtKosu
where AtKosu.KosuKodu = gm_KosuTahminNominal.gm_KosuId
and  AtKosu.KosuKodu = 47956
and AtKosu.AtKodu = gm_KosuTahminNominal.gm_AtId
   order by gm_KosuId, gm_RegressionPredicted



   select distinct gm_experiment from gm_KosuTahminNominal

   select  *  from gm_KosuTahminNominal where gm_experiment = 'Son7Kosu-SMO-0' and gm_kosuId = 0;


   select count(*) as count from (select gm_KosuId, gm_AtId, gm_Prediction,gm_YesPrediction,gm_NoPrediction ,  gm_RegressionActual, gm_RegressionPredicted, gm_RegressionError,
b.SONUCNO  as Kacinci, b.Ganyan as Ganyan,
Ikili, SiraliIkili
from gm_KosuTahminNominal a,
AtKosu b,
(select ATKOSU.KOSUKODU as KOSUKODU, ATKODU, Tutar as Ikili
from ATKOSU
LEFT OUTER JOIN Bahisler
ON  ATKOSU.KOSUKODU = Bahisler.KOSUKODU
and Bahisler.BahisTipAdi='İKİLİ' ) as c,
(select ATKOSU.KOSUKODU as KOSUKODU, ATKODU, Tutar as SiraliIkili
from ATKOSU
LEFT OUTER JOIN Bahisler
ON  ATKOSU.KOSUKODU = Bahisler.KOSUKODU
and Bahisler.BahisTipAdi='SIRALI İKİLİ' ) as  d
 where a.gm_KosuId = b.KOSUKODU
 and a.gm_AtId = b.ATKODU
 and a.gm_KosuId = c.KOSUKODU
 and a.gm_AtId = c.ATKODU
 and a.gm_KosuId = d.KOSUKODU
 and a.gm_AtId = d.ATKODU
 and a.gm_Experiment = 'test-predictions'
 order by gm_kosuId asc, b.SONUCNO asc  ) as Analiz



   selec

   select count (*) from (
   select sonucNo,gm_RegressionPredicted,ganyan, * from gm_KosuTahminNominal  , AtKosu
where AtKosu.KosuKodu = gm_KosuTahminNominal.gm_KosuId

and AtKosu.AtKodu = gm_KosuTahminNominal.gm_AtId
and AtKosu.KosuKodu in (    select gm_KosuId from gm_KosuTahminNominal where gm_RegressionActual = 0 and gm_RegressionPredicted <100 )
order by gm_KosuId, gm_RegressionPredicted
) A
group by gm_KosuId


   select * from AtKosu where KosuKodu in (
    select B.kosukodu from
    (select kosukodu,count(*) as atcount from AtKosu group by kosukodu) A,
    (select kosukodu,count(*) as atcount from AtKosu where son7derecefarkiortalamasi is not null group by kosukodu ) B
    where A.kosukodu = B.kosukodu
    AND A.atcount = B.atcount)
    AND KOSUTARIHI<='2011-08-01'
    AND KOSUTARIHI>='2011-05-01'
    AND BirinciIleDereceFarki is not null    AND substring(KosuCinsDetayAdi from 1 for 8) <> 'Handikap'     order by KosuKodu asc,SonucNo asc



   -- 2011-05-01

   select sonucNo , * from AtKosu where Atkodu = 21354 order by KosuTarihi desc;

      select sonucNo , * from AtKosu where Atkodu = 19662 order by KosuTarihi desc;


delete from gm_KosuTahminNominal;


select * from gm_KosuTahminNominal;

select from AtKosu where KosuKodu in (
    select B.kosukodu from
    (select kosukodu,count(*) as atcount from AtKosu group by kosukodu) A,
    (select kosukodu,count(*) as atcount from AtKosu where son7derecefarkiortalamasi is not null group by kosukodu ) B
    where A.kosukodu = B.kosukodu
    AND A.atcount = B.atcount)
    AND KOSUTARIHI<='2011-08-01'
    AND KOSUTARIHI>='2011-05-01'
    AND BirinciIleDereceFarki is not null
      order by KosuKodu asc,SonucNo asc


      select sonucno,* from gm_KosuTahminNominal a , atkosu  b
      where  a.gm_kosuId = b.KosuKodu and a.gm_AtId = b.AtKodu
      and gm_Experiment = 'test-predictions'
      order by gm_kosuId, gm_yesprediction desc;

      select * from gm_KosuTahminNominal where gm_Experiment = 'test-predictions'

      select AtKodu from AtKosu where KosuKodu = 49283;


select * from AtKosu where bitirisOrtalamasi  is not null;

select * from KosuSonucuAtlar where AtKodu = 74246;

      select * from Bahisler;


      select * from changelog;

      select gm_KosuId, gm_AtId, gm_Prediction,gm_YesPrediction,gm_NoPrediction ,  gm_RegressionActual, gm_RegressionPredicted, gm_RegressionError,
b.SONUCNO  as Kacinci, b.Ganyan as Ganyan,
Ikili, SiraliIkili
from gm_KosuTahminNominal a,
AtKosu b,
(select ATKOSU.KOSUKODU as KOSUKODU, ATKODU, Tutar as Ikili
from ATKOSU
LEFT OUTER JOIN Bahisler
ON  ATKOSU.KOSUKODU = Bahisler.KOSUKODU
and Bahisler.BahisTipAdi='İKİLİ' ) as c,
(select ATKOSU.KOSUKODU as KOSUKODU, ATKODU, Tutar as SiraliIkili
from ATKOSU
LEFT OUTER JOIN Bahisler
ON  ATKOSU.KOSUKODU = Bahisler.KOSUKODU
and Bahisler.BahisTipAdi='SIRALI İKİLİ' ) as  d
 where a.gm_KosuId = b.KOSUKODU
 and a.gm_AtId = b.ATKODU
 and a.gm_KosuId = c.KOSUKODU
 and a.gm_AtId = c.ATKODU
 and a.gm_KosuId = d.KOSUKODU
 and a.gm_AtId = d.ATKODU
 and a.gm_Experiment = 'test-predictions'
 order by gm_kosuId asc, b.SONUCNO asc

 select * from changelog

 create table RegressionPrediction (
 experiment varchar(200) NOT NULL,
 instanceId varchar(200) NOT NULL,
 actual real NOT NULL ,
 predicted real NOT NULL,
 error  real NOT NULL,
 PRIMARY KEY  ( experiment, instanceId ) );

 drop table RegressionPrediction;


 create table ClassificationPrediction (
 experiment varchar(200) NOT NULL,
 instanceId varchar(200) NOT NULL,
 actual varchar(200) NOT NULL,
 actualIndex integer NOT NULL,
 predicted varchar(200) NOT NULL,
 predictedIndex integer NOT NULL,
 error  boolean NULL,
 PRIMARY KEY  ( experiment, instanceId ) );


 drop table ClassificationPrediction;

 drop table Distribution;


 CREATE INDEX Distribution_classificationClass on Distribution(classificationClass)

  create table Distribution (
 experiment varchar(200) NOT NULL,
 instanceId varchar(200) NOT NULL,
 classificationClass varchar(200) NOT NULL,
 distribution real NOT NULL,
 PRIMARY KEY  ( experiment, instanceId,classificationClass ) );

 drop table distribution;

 select * from ATKosu;

 select * from ClassificationPrediction   ;

  alter table atkosu add column  KosuKosuAtKodu varchar(200)

  CREATE INDEX AtKosu_KosuKoduAtKodu on atkosu(KosuKoduAtKodu)

  alter table atkosu add column  KosuKoduAtKodu varchar(200) ;

  DROP INDEX AtKosu_KosuKoduAtKodu;

 alter table AtKosu drop column KosukoduAtKodu;

 select * from AtKosu;


update AtKosu set KosuKoduAtKodu = KosuKodu || '_' || AtKodu;

select * from AtKosu where kosukodu = 18527;


  select ganyan, a.* from RegressionPrediction a, AtKosu b
  where experiment = 'KStar'
  and a.instanceId = b.KosuKoduAtKodu
  order by b.KosuKodu desc, predicted asc;


  select * from RegressionPrediction a  where experiment = 'KStar'
  order by substring(instanceid from 1 for position('_' in instanceid) ) desc, predicted asc;


  select a.experiment , '800'|| b.KosuKodu || '_' || '800' || b.AtKodu instanceId,actual,predicted,error from RegressionPrediction a, AtKosu b
  where experiment = 'KStar'
  and a.instanceId = b.KosuKoduAtKodu
  order by b.KosuKodu desc, predicted asc;



  select
  '2001-03-28' kosutarihi,
  hipodromkodu, hipodromyeri, cim, kum,
  '800' || kosukodu kosukodu,
  kosuno, onemlikosuadi, kosucinsdetayadi, grupadi, ikramiye1, ikramiye2, ikramiye3, ikramiye4, mesafe, pistuzunadi,
  '800' || atkodu atkodu,
  atno, atadi, sonkilo, jokeykisaadi, sahipkisaadi, startno, puan, puankum, annebabakodu, annebabakodu1, antrenorkodu, sahipkodu, jokeykodu, sonucno, derece, ganyan, atikramiye, birincilikyuzdesi, ikincilikyuzdesi, ucunculukyuzdesi, ikinciyegirisyuzdesi, ucuncuyegirisyuzdesi, bitirisortalamasi, son3derecefarkiortalamasi, son7derecefarkiortalamasi, birinciilederecefarki, son7birincilikyuzdesi, son7ikincilikyuzdesi, son7ucunculukyuzdesi, son7bitirisortalamasi,
  '800'|| b.KosuKodu || '_' || '800' || b.AtKodu kosukoduatkodu
   from RegressionPrediction a, AtKosu b
  where experiment = 'KStar'
  and a.instanceId = b.KosuKoduAtKodu
  order by b.KosuKodu desc, predicted asc;

  select * from information_schema.columns ;

select concat(column_name) || ',' from
  (select column_name from information_schema.columns where
table_name='atkosu') a;

select  * FROM ATKOSU where kosutarihi = '2001-03-28' ORDER BY KOSUTARIHI ASC;



 select distinct kosutarihi from RegressionPrediction a, AtKosu b
  where experiment = 'test-son7kosu-kstar-experiment'
  and a.instanceId = b.KosuKoduAtKodu

  order by b.KosuKodu desc, predicted asc;


delete from AtKosu where kosutarihi = '2011-03-27' and substring (id || '' from 1 for 3) = '800'

select count(*) from   AtKosu where kosutarihi = '2001-03-28'

select count(*) from   AtKosu where experiment = 'test-son7kosu-kstar-experiment'  ;

select * from AtKosu order by KosuTarihi asc;

delete from AtKosu where KosuTarihi < '2001-04-01'

  select sum(genyen) from (
  select ganyan genyen , a.*,b.* from RegressionPrediction a, AtKosu b
  where experiment = 'test-son7kosu-kstar-experiment'
  and a.instanceId = b.KosuKoduAtKodu
  and predicted < 2.1
and actual =1
  order by b.KosuKodu desc, predicted asc
   ) a

 select ganyan, a.*,b.* from RegressionPrediction a, AtKosu b
  where experiment = 'KStar'
  and a.instanceId = b.KosuKoduAtKodu
  order by b.KosuKodu desc, predicted asc;

  drop index AtKosu_KosuKoduAtKodu;

  CREATE UNIQUE INDEX AtKosu_KosuKoduAtKodu on atkosu(KosuKoduAtKodu);

  select version();



  select bahistipkodu, bahistipadi  from bahisler group by bahistipkodu, bahistipadi;

  select * from Bahisler;

  select '800' || a.kosukodu ,
  a.bahistipkodu ,a.bahistipadi ,a.tutar ,a.sonuc ,a.aciklama ,a.cikan1atlatutar ,a.cikan2atlatutar ,a.cikan3atlatutar ,a.farklar ,a.kosutarihi ,a.hipodromkodu ,a.hipodromyeri
  from Bahisler a, AtKosu b, RegressionPrediction c
  where a.kosukodu || '' = substring(b.kosukoduatkodu from 4 for position('_' in b.kosukoduatkodu) -4 )
  and b.kosukoduatkodu = c.instanceid
  and c.experiment= 'test-son7kosu-kstar-experiment'
  order by b.KosuKodu desc, predicted asc ,bahistipkodu



  select '800' || id id,
  '800' || kosukodu kosukodu,
  bahistipkodu ,bahistipadi ,tutar ,sonuc ,aciklama ,cikan1atlatutar ,cikan2atlatutar ,cikan3atlatutar ,farklar ,
  '2001-03-28' kosutarihi,
  hipodromkodu ,hipodromyeri
  from Bahisler where kosukodu::varchar(255) in (
  select substring(kosukodu::varchar(255) from 4 for length(kosukodu::varchar(255)) -3 )    from (
  select kosukodu from
  (
  select distinct b.kosukodu from  AtKosu b, RegressionPrediction c
  where b.kosukoduatkodu = c.instanceid
  and c.experiment= 'test-son7kosu-kstar-experiment'
  ) e
   )  f )
  order by kosukodu desc, bahistipkodu




  select substring(kosukoduatkodu from 4 for position('_' in kosukoduatkodu) -4 )  from AtKosu

  select concat(column_name) || ',' from
    (select column_name from information_schema.columns where
  table_name='bahisler') a;

  select column_name from information_schema.columns where
  table_name='bahisler'



    select sum(genyen) from (
    select  ganyan genyen , a.*,b.* from RegressionPrediction a, AtKosu b
    where experiment = 'test-son7kosu-kstar-experiment'
    and a.instanceId = b.KosuKoduAtKodu
    and predicted < 2.1
  and actual =1
    order by b.KosuKodu desc, predicted asc
     ) a



    select sum(genyen) from (
    select c.tutar, substring(a.instanceid from 4 for position('_' in instanceid) -4 ), ganyan genyen , a.*,b.* from RegressionPrediction a, AtKosu b, Bahisler c
    where experiment = 'test-son7kosu-kstar-experiment'
    and a.instanceId = b.KosuKoduAtKodu
    and predicted < 3
   and c.Kosukodu || '' = substring(a.instanceid from 4 for position('_' in instanceid) -4 )
  -- and (c.bahistipadi = 'İKİLİ' or c.bahistipadi = 'SIRALI İKİLİ' )
  and c.bahistipadi = 'İKİLİ'
  --and actual =1
  and b.kosukodu = 80048862
    order by b.KosuKodu desc, predicted asc
     ) a


     select * from RegressionPrediction where experiment = 'test-son7kosu-kstar-experiment' order by substring(instanceid from 4 for position('_' in instanceid) -4 ), predicted

     ALTER TABLE RegressionPrediction ADD CONSTRAINT RegressionPrediction_instance_id_fk FOREIGN KEY (instanceId) REFERENCES AtKosu (KosuKoduAtKodu) MATCH FULL;


     select substring(instanceid from 1 for position('_' in instanceid)-1 ) from RegressionPrediction;


     SELECT a  FROM RegressionPrediction AS a
  LEFT JOIN a.children AS c
  WHERE p.state = 1
  ORDER BY p.modified


  SELECT a.*,b.*
      FROM RegressionPrediction a LEFT OUTER JOIN AtKosu b  ON (a.instanceId = b.KosuKoduAtKodu) where a.experiment = 'test-regression-predictions';


      select * from AtKosu;

      ALTER TABLE AtKosu DROP COLUMN id;

      ALTER TABLE AtKosu ADD PRIMARY KEY (KosuKoduAtKodu);

      alter table bahisler drop constraint fk_Bahisler_KOSUKODU


      // ikili kazanc
      select sum (tutar) from (
      select tutar,kosukodu from (
  	    select c.tutar , a.kosukodu, b.*  from AtKosu a, RegressionPrediction b, Bahisler c
  	    where a.kosukoduatkodu = b.instanceid
  	    and a.kosukodu in  (80049139, 80048862, 80048495, 80048422)
  	    and experiment = 'test-son7kosu-kstar-experiment'
  	    and c.kosukodu = a.kosukodu
  	    and c.bahistipkodu = 7
  	    and predicted < 3.0
  	    order by a.kosukodu desc, predicted asc
  	    ) b
  	    group by kosukodu,tutar
      )  a

  // ne kadar verirdik
  select sum (  (count * (count-1))/2 ) from (
  	select * from (
  		select a.kosukodu,count(*)  as count from AtKosu a, RegressionPrediction b, Bahisler c
  		where a.kosukoduatkodu = b.instanceid
  		and experiment = 'test-son7kosu-kstar-experiment'
  		and predicted < 3.0
  		and c.kosukodu = a.kosukodu
  		and c.bahistipkodu = 7
  		group by a.kosukodu
  		order by a.kosukodu desc
  		) a
  		where count >= 2
  		order by kosukodu
  	) b

  	select * from bahisler where kosukodu = 80048862;




  select sum(genyen) from (
  	select  ganyan genyen , a.*,b.* from RegressionPrediction a, AtKosu b
  	where experiment = 'test-son7kosu-kstar-experiment'
  	and a.instanceId = b.KosuKoduAtKodu
  	and predicted < 2.1
  	and actual =1
  	order by b.KosuKodu desc, predicted asc
  ) a

  select count(*) from(
  	select  ganyan genyen , a.*,b.* from RegressionPrediction a, AtKosu b
  	where experiment = 'test-son7kosu-kstar-experiment'
  	and a.instanceId = b.KosuKoduAtKodu
  	and predicted < 2.1
  	order by b.KosuKodu desc, predicted asc
  ) a




   select sum (tutar) from (
  	 select tutar,kosukodu from (
  		 select c.tutar , a.kosukodu, a.sonucno  from AtKosu a, RegressionPrediction b, Bahisler c
  		 where a.kosukoduatkodu = b.instanceid
  		 and experiment = 'test-son7kosu-kstar-experiment'
  		 and c.kosukodu = a.kosukodu
  		 and c.bahistipkodu = 7
  		 and predicted < 3.0
  		 order by a.kosukodu desc, predicted asc
  	 ) b
   group by kosukodu,tutar
   )  a

   // ikili kazanc
   select sum(tutar) from Bahisler where kosukodu in(
  	 select kosukodu from (
  		 select kosukodu,count(*) count  from AtKosu a, RegressionPrediction b
  		 where kosukoduatkodu = b.instanceid
  		 and experiment = 'test-son7kosu-kstar-experiment'
  		 and predicted < 3.0
  		 and (sonucno =1 or sonucno =2)
  		 group by kosukodu
  		 order by kosukodu desc
  		 ) a
  		 where count =2
  	)
  	and bahistipkodu = 7


  	     select sum (  (count * (count-1))/2 ) from (
       select * from (
       select a.kosukodu,count(*)  as count from AtKosu a, RegressionPrediction b, Bahisler c
       where a.kosukoduatkodu = b.instanceid
       and experiment = 'test-son7kosu-kstar-experiment'
       and predicted < 3.0
       and c.kosukodu = a.kosukodu
       and c.bahistipkodu = 7
       group by a.kosukodu
       order by a.kosukodu desc
       ) a
       where count >= 2
       order by kosukodu
       ) b


  		     select count(*) from(
       select  ganyan genyen , a.*,b.* from RegressionPrediction a, AtKosu b
       where experiment = 'test-son7kosu-kstar-experiment'
       and a.instanceId = b.KosuKoduAtKodu
       and predicted < 2.1
       order by b.KosuKodu desc, predicted asc
       ) a

       select * from Bahisler where kosukodu = 47947;

       		 select  c.*  from AtKosu a, Bahisler c
  		 where c.kosukodu = a.kosukodu
  		 and c.bahistipkodu = 12
  		 and a.kosukodu = 47947
  		 		 order by a.kosukodu desc


  select * from AtKosu where kosuKodu = 49078;

   select  a.*  from AtKosu a, Bahisler c
  where c.kosukodu = a.kosukodu
  and c.bahistipkodu = 12
  --and a.kosukodu = 49078
  and a.KOSUTARIHI<='2011-08-01'
   AND a.KOSUTARIHI>='2011-05-01'
  order by a.kosukodu desc
  
  
  
  
  
  select * from RegressionPrediction where experiment = 'KStarNominal'


select distinct experiment from RegressionPrediction where experiment = 'KStar';

select distinct experiment from ClassificationPrediction

select * from ClassificationPrediction where experiment = 'KStarNominal' order 

select * from Distribution where experiment = 'KStarNominal'



select * from Distribution where experiment = 'test-son7kosu-nominal-kstar-experiment'

select distinct experiment from Distribution;

 id=80049189_80025209


select * from ClassificationPrediction where instanceid = '80048027_80023003'

select * from Distribution where instanceid = '80048027_80023003'


delete from Distribution;



// oynanabilir kosular 
select count (*) from (
	select distinct KosuKodu from 
	(
		select * from ClassificationPrediction  a, Distribution b, AtKosu c
		where a.experiment = 'test-son7kosu-nominal-kstar-experiment'
		and a.experiment = b.experiment
		and a.instanceid = b.instanceid
		and c.KosuKoduAtKodu = a.instanceId
		and b.classificationclass = 'Y'
		and b.distribution > (1 - 0.84600002)
		order by c.KosuKodu, b.distribution desc
	) b
) c

// kazanc ne olurdu 
select  suganyan from 
(
		select sum(ganyan) from ClassificationPrediction  a, Distribution b, AtKosu c
		where a.experiment = 'test-son7kosu-nominal-kstar-experiment'
		and a.experiment = b.experiment
		and a.instanceid = b.instanceid
		and c.KosuKoduAtKodu = a.instanceId
		and b.classificationclass = 'Y'
		and b.distribution > (1 - 0.84600002)
		and actual = 'Y'
	
) 
		
		
		

select * from  Distribution
where experiment = 'test-son7kosu-nominal-kstar-experiment'
and classificationclass = 'Y'
order by distribution desc


select sum (  (count * (count-1))/2 ) from (
	select kosukodu,count from (
		select c.kosukodu,count(*) count from ClassificationPrediction  a, Distribution b, AtKosu c
		where a.experiment = 'test-son7kosu-nominal-kstar-experiment'
		and a.experiment = b.experiment
		and a.instanceid = b.instanceid
		and c.KosuKoduAtKodu = a.instanceId
		and b.distribution > (1 - 0.91600001)
--		and (sonucno =1 or sonucno =2)
		and b.classificationclass = 'Y'
		group by kosukodu
		order by kosukodu desc
     ) a where count >=2
) b

    select sum(tutar) from Bahisler where kosukodu in(
	     select kosukodu,count from (
		     select c.kosukodu,count(*) count from ClassificationPrediction  a, Distribution b, AtKosu c
		     where a.experiment = 'test-son7kosu-nominal-kstar-experiment'
		     and a.experiment = b.experiment
		     and a.instanceid = b.instanceid
		     and c.KosuKoduAtKodu = a.instanceId
		     and b.distribution > (1 - 0.91600001)
	--	     and (sonucno =1 or sonucno =2)
		     and b.classificationclass = 'Y'
		     group by kosukodu
		     order by kosukodu desc
		     ) a where count >=2
     )
     and bahistipkodu = 7
     
     
     
select sum (  (count * (count-1))/2 ) from (
	 select kosukodu,count from (
		 select c.kosukodu,count(*) count from ClassificationPrediction  a, Distribution b, AtKosu c,Bahisler d
		 where a.experiment = 'test-son7kosu-nominal-kstar-experiment'
		 and a.experiment = b.experiment
		 and a.instanceid = b.instanceid
		 and c.KosuKoduAtKodu = a.instanceId
		 and b.distribution > (1 - 0.91600001)
		--	     and (sonucno =1 or sonucno =2)
		 and b.classificationclass = 'Y'
		 and d.kosukodu = c.kosukodu
		 and bahistipkodu = 7
		 group by c.kosukodu
		 order by c.kosukodu desc
	 ) a where count >=2
 ) b


     
     
     
     	     select c.sonucno, c.atkodu,c.atadi,  * from ClassificationPrediction  a, Distribution b, AtKosu c
	     where a.experiment = 'test-son7kosu-nominal-kstar-experiment'
	     and a.experiment = b.experiment
	     and a.instanceid = b.instanceid
	     and c.KosuKoduAtKodu = a.instanceId
	     and b.distribution > (1 - 0.91600001)
	     and b.classificationclass = 'Y'

	     order by kosukodu desc
	     
	     select * from atkosu where kosukodi = 
	     
	     


		select c.sonucno, *  from ClassificationPrediction  a, Distribution b, AtKosu c
		where a.experiment = 'test-son7kosu-nominal-kstar-experiment'
		and a.experiment = b.experiment
		and a.instanceid = b.instanceid
		and c.KosuKoduAtKodu = a.instanceId
		and b.distribution > (1 - 0.91600001)
		and b.classificationclass = 'Y'
		and (sonucno =1 or sonucno =2)
		order by kosukodu desc
     
     
     
     
          select sum(tutar) from Bahisler where kosukodu in(
     select kosukodu from (
     select kosukodu,count(*) count  from AtKosu a, RegressionPrediction b
     where kosukoduatkodu = b.instanceid
     and experiment = 'test-son7kosu-kstar-experiment'
     and predicted < 4.2459998
     and (sonucno =1 or sonucno =2)
     group by kosukodu
     order by kosukodu desc
     ) a
     where count >=2
     )
     and bahistipkodu = 7



     select kosukodu,count(*) count  from AtKosu a, RegressionPrediction b
     where kosukoduatkodu = b.instanceid
     and experiment = 'test-son7kosu-kstar-experiment'
     and predicted < 3.0
     and (sonucno =1 or sonucno =2)
   
     order by kosukodu desc
     
drop table ExperimentResult;

drop table Kazanc;

drop table BilinenKosu;
     
     create table ExperimentResult (
 experiment varchar(200) NOT NULL ,
 classification boolean NOT NULL,
correct real,
pctCorrect real,
incorrect real,
pctIncorrect real,
kappa real,
totalCost real,
avgCost real,
KBRelativeInformation real,
KBInformation real,
KBMeanInformation real,
SFPriorEntropy real,
SFMeanPriorEntropy real,
SFSchemeEntropy real,
SFMeanSchemeEntropy real,
SFEntropyGain real,
SFMeanEntropyGain real,
meanAbsoluteError real,
rootMeanSquaredError real,
relativeAbsoluteError real,
rootRelativeSquaredError real,
correlationCoefficient real,
project text,
arff bytea,
 PRIMARY KEY  ( experiment ) ); 
 
 CREATE INDEX ExperimentResult_experiment on ExperimentResult(experiment);
 
create table Kazanc (
id SERIAL ,
kacKosuVardi integer NOT NULL ,
threshold real,
kacKosudaOynardik integer NOT NULL ,
kacKosudaBilirdik integer NOT NULL , 
neKadarVerirdik real  NOT NULL ,
kazancOraninKacOlurdu real NOT NULL ,
yuzdeKacindaOynardik real  check ( yuzdeKacindaOynardik >= 0.0 and yuzdeKacindaOynardik <=100.0 ),
yuzdeKacindaBilirdik real  check ( yuzdeKacindaBilirdik >= 0.0 and yuzdeKacindaBilirdik <=100.0 ),
kacliraKazanirdik real NOT NULL ,
kacKosudaOynanabilirdi integer ,
gameType varchar (100)  check ( gameType in ('KazancGanyan','KazancIkili','KazancSiraliIkili') ),
type varchar (100)  check ( type in ('oynamayuzdesi-threshold') ),
index int ,
experiment varchar(200) NOT NULL  ,
PRIMARY KEY  ( id )
); 

 alter table Kazanc add CONSTRAINT fk_Kazanc_experiment Foreign key (experiment) references ExperimentResult(experiment);

 ALTER TABLE Kazanc ADD CONSTRAINT Kazanc_unique_constraint UNIQUE (experiment,type,gameType,index);

 CREATE INDEX Kazanc_experiment on Kazanc(experiment);
 
 
 create table BilinenKosu (
id serial  ,
kazanc_id integer NOT NULL,
KOSUKODU numeric NOT NULL, 
PRIMARY KEY  ( id )
); 


 CREATE INDEX BilinenKosu_kazanc_id on BilinenKosu(kazanc_id);
 
 alter table BilinenKosu add CONSTRAINT fk_BilinenKosu_kazancid Foreign key (kazanc_id) references Kazanc(id);

 ALTER TABLE BilinenKosu ADD CONSTRAINT BilinenKosu_unique_constraint UNIQUE (kazanc_id,KOSUKODU);
 
select  * from  ExperimentResult a, Kazanc b, BilinenKosu c
where a.experiment = b.experiment
and c.kazanc_id = b.id
--and a.experiment = 'BPJtQ';

