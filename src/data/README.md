Datasets
--------

The `*gram*` data samples come from [the Google Books NGram Viewer](https://books.google.com/ngrams) project,
and the raw datasets are available for free at [https://books.google.com/ngrams/datasets](https://books.google.com/ngrams/datasets).

To generate the samples, download, uncompress and rename the following raw datasets:
* [1gram_a.csv](http://storage.googleapis.com/books/ngrams/books/googlebooks-eng-all-1gram-20120701-a.gz)

The `*gram*` data samples were generated with the following commands
* `head -n 97500 1gram_a.csv | tail -n 2000 | egrep -v _ >`**`t1gram.csv`**

Creating and Importing the Data in MySql
----------------------------------------

* `create table 1gram_a (n_gram text, year int(11) default null, match_count int(11) default null, volume_count int(11) default null);`
* `mysqlimport --local -u $USER -p $DB 1gram_a.csv`

Running Compiled Queries
------------------------

From the `../out` directory, pick a `$QUERY` and some `$DATA`:
* `export QUERY=query_t1gram2.check.scala`
* `export DATA=../data/t1gram.csv`
* `cat ../test/scala/lms/tutorial/scannerlib.scala $QUERY query.scala >run.scala`
* `scalac run.scala`
* `scala scala.lms.tutorial.query $DATA`
