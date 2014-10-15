package com.yelp.query

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.server.TwitterServer
import com.twitter.util.{Await, Future}
import com.twitter.zipkin.cassandra.CassieSpanStoreFactory
import com.twitter.zipkin.common.Span
import com.twitter.zipkin.storage.WriteSpanStore
import com.twitter.zipkin.query.ZipkinQueryServerFactory

object ZipkinQueryServer extends TwitterServer
  with CassieSpanStoreFactory
  with ZipkinQueryServerFactory
{
  def main() {
    val spanStore = newCassandraStore()
    val query = newQueryServer(spanStore)
    onExit { query.close() }
    Await.ready(query)
  }
}
