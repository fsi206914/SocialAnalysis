package preComp

import com.typesafe.config.ConfigFactory
import scala.io.Source
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Map
import scala.collection.mutable.HashSet
import scala.util.Random
import scala.math

object CurveMutualFrds{

  val rand = new Random(System.currentTimeMillis())

  def findNumMutualFrds(frds1: ArrayBuffer[Int], frds2: ArrayBuffer[Int]) = {
    frds1.toSet.intersect(frds2.toSet).size
  }

  def apply(frdsMap: Map[Int, ArrayBuffer[Int]]) = {
  
    /*
     * In frdsMap, the frdsRelationship might be single-direction. 
     * The blow block make the frdsRelationship have the double-direction map.
     */

    val mutFrdsRelationship = Map[Int, (Int, Int)]();
    for(i<- 1 to 10000) mutFrdsRelationship += i -> (0,0)

    val totalP = frdsMap.size;
    var loopCount = 0;
    for((k1,v1)<- frdsMap){
      loopCount += 1;
      if(loopCount % 100 == 0)
        println("percent = "+ loopCount.toDouble/totalP);
      for((k2,v2)<- frdsMap; if(k1 < k2) ){

        if(rand.nextDouble()<0.001){
          val num = findNumMutualFrds(v1, v2)
          if(num>0){
            val (tup1, tup2) = mutFrdsRelationship(num)
            if(v1.contains(k2)){
              mutFrdsRelationship(num) = (tup1+1, tup2+1)
            }
            else{
              mutFrdsRelationship(num) = (tup1, tup2+1)
            }
          }
        }
      }
    }

    import java.io.PrintWriter
    val S = new PrintWriter("test_5.txt")
    for (i <- 1 to 200) {
      val (a,b) = mutFrdsRelationship(i);
      S.println(i+ " " + a + "  " + b + "  "+ a.toDouble/b)
    }
    S.close()
  }
}


object CurveCommFrds{

  val rand = new Random(System.currentTimeMillis())

  def findNumMutualFrds(frds1: ArrayBuffer[Int], frds2: ArrayBuffer[Int]) = {
    if(frds1 == null || frds2 == null) 0
    else{
      // print(frds1)
      // println(frds2)
      frds1.toSet.intersect(frds2.toSet).size
    }
  }

  def apply(commMap: Map[Int, ArrayBuffer[Int]], frdsMap: Map[Int, ArrayBuffer[Int]]) = {
  
    /*
     * In frdsMap, the frdsRelationship might be single-direction. 
     * The blow block make the frdsRelationship have the double-direction map.
     */
    val commFrdsRelationship = Map[Int, (Int, Int)]();
    for(i<- 1 to 1000) commFrdsRelationship += i -> (0,0)

    val totalP = commMap.size;
    var loopCount = 0;
    for((k1,v1)<- commMap){
      loopCount += 1;
      if(loopCount % 100 == 0)
        println("percent = "+ loopCount.toDouble/totalP);
      for((k2,v2)<- commMap; if(k1 < k2) ){

        if(rand.nextDouble()<0.1){
          val num = findNumMutualFrds(v1, v2)
          if(num>0){
            val (tup1, tup2) = commFrdsRelationship(num)

            if(frdsMap(k1).contains(k2)){
              commFrdsRelationship(num) = (tup1+1, tup2+1)
            }
            else{
              commFrdsRelationship(num) = (tup1, tup2+1)
            }
          }
        }
      }
    }

    import java.io.PrintWriter
    val S = new PrintWriter("test_comm_20.txt")
    for (i <- 1 to 200) {
      val (a,b) = commFrdsRelationship(i);
      S.println(i+ " " + a + "  " + b + "  "+ a.toDouble/b)
    }
    S.close()
  }
}