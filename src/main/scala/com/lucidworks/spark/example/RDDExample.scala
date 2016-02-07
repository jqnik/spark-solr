package com.lucidworks.spark.example

import com.lucidworks.spark.{SolrScalaRDD, SparkApp}
import org.apache.commons.cli.{CommandLine, Option}
import org.apache.spark.{Logging, SparkConf, SparkContext}

class RDDExample extends SparkApp.RDDProcessor with Logging {

  override def getName: String = "old-rdd-example"

  override def getOptions: Array[Option] = Array(
    Option.builder().longOpt("query").hasArg.required(true).desc("Query to field").build()
  )

  override def run(conf: SparkConf, cli: CommandLine): Int = {
    val zkHost = cli.getOptionValue("zkHost", "localhost:9983")
    val collection = cli.getOptionValue("collection", "collection1")
    val queryStr = cli.getOptionValue("query", "*:*")

    val sc = new SparkContext(conf)
    val rdd = new SolrScalaRDD(zkHost, collection, sc)
    val count = rdd.count()

    log.info("Count is " + count)
    sc.stop()
    0
  }
}