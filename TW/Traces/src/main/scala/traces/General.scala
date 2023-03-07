package traces

import scala.annotation.tailrec

object General {

  def main(args: Array[String]): Unit = {
    // Wczytaj argumenty, a jesli ich nie ma to ustaw nazwe na domyslna
    val name = if (args.length==1){
      println(args(0))
      args(0)
    }
    else{
      "in.txt"
    }
    // Wczytujemy i przygotowujemy dane
    val data = new Data(name)
    // Wypisujemy wczytane i przerobione dane
//    println(data.Alphabet)
//    println(data.Word)
//    println(data.Prods)
    // Przygotowujemy produkcje, wykorzystujaco wczytane dane
    val prods = new Productions(data.Prods)


    // Wypisujemy wyniki

    print("\nD={")
    prods.D.init.foreach(pair => print(s"$pair,"))
    print(prods.D.last)
    print("}\n\nI={")
    prods.I.init.foreach(pair => print(s"$pair,"))
    print(prods.I.last)
    print("}\n\nFNF([w])=")
    solveFnF(data, prods).foreach(cla => {
      print('(')
      cla.sorted.foreach(print(_))
      print(')')
    })

    val gra =  graph(data, prods)
    println("\n\ndigraph g{")
    gra.foreach(edge => {
      println(s"${edge._1+1} -> ${edge._2+1}")
    })
    data.Word.zipWithIndex.foreach(vertex => {
      println(s"${vertex._2+1}[label=${vertex._1}]")
    })
    print("}\n\nGraphFNF([w])=")
    solveGraphFnF(data, gra).foreach(cla => {
      print('(')
      cla.sorted.foreach(print(_))
      print(')')
    })
    println()
  }


  // Wyznaczamy postac normalna Foaty
  def solveFnF(data: Data, prods: Productions): List[List[Char]] = {
    // countClass tworzy nam tupla literki i numeru klasy do jakiego zostala przypisana
    // jako argumenty dostaje przerobiona czesc slowa i czesc slowa do przerobienia (w danym kroku sprawdzamy todo.head)
    // wolamy rekurencyjnie na samym koncu, wiec mozemy zrobic optymalizacje rekurencji ogonowej
    // danej literce przypisujemy klase bedaca najwieksza z juz przerobionych, z ktorymi literka jest w relacji + 1
    @tailrec
    def countClass(done: List[(Char, Int)] = Nil, todo: List[Char] = data.Word.toList): List[(Char, Int)] = {
      if(todo.isEmpty) done
      else if(!done.exists(rel => prods.D contains(rel._1, todo.head))) countClass((todo.head, 0) :: done, todo.tail)
      else countClass((todo.head, done.filter(rel => prods.D contains (rel._1, todo.head)).maxBy(_._2)._2+1) :: done, todo.tail)
    }
    // grupujemy wynik po klasie i przygotowujemy do wypisania
    countClass().groupBy(_._2).toList.sortBy(_._1).map(_._2.map(_._1))
  }


  // Wyznaczamy graf zaleznosci w postaci minimalnej dla danego slowa
  def graph(data: Data, prods: Productions): List[(Int, Int)] = {
    // Tworzymy graf z zaznaczonymi wszystkimi zaleznosciami, ale taki by zadna krawedz nie szla od pozniejszej litery do wczesniejszej
    // Graf reprezentujemy jako liste krawedzi
    val word: List[(Char, Int)] = data.Word.zipWithIndex.toList
    val compl: List[(Int, Int)] = word.flatMap(pair => {
      word.filter(rel=>{rel._2>pair._2 && prods.D.contains((pair._1, rel._1))}).map(rel => (pair._2, rel._2))
    })

    // indPath przyjmuje graf w postaci listy krawedzi, krawedz ktora sprawdzamy czy jest zbedna i obecnego wierzcholka
    // podobnie do dfs'a przeszukujemy graf zaczynajac w lewym koncu krawedzi i sprawdzamy czy jestesmy w stanie dojsc do prawego konca, pomijajac bezposrednie polaczenie
    def indPath(graf: List[(Int, Int)], redund: (Int, Int), curr: Int): Boolean = {
      if(curr == redund._2) true
      else graf.filterNot(_ == redund).filter(_._1 == curr).exists(edge => indPath(graf, redund, edge._2))
    }

    // Przygotowujemy do wypisania
    compl.filterNot(edge => indPath(compl, edge, edge._1))
  }


  // Wyznaczamy postac normalna Foaty, wykorzystujac wyznaczony wczesniej graf (w postaci listy krawedzi)
  // Algorytm dziala bardzo podobnie do solveFnF, tylko zamiast sprawdzania czy literki sa w relacji, sprawdza czy istnieje taka krawedz
  def solveGraphFnF(data: Data, gra: List[(Int, Int)]): List[List[Char]] = {
    // Bardzo analogiczna funkcja do countClass z solveFnF
    // Dla kazdej krawedzi przypisujemy najwiekszy numer, z juz wczesniej przypisanych krawedziom, bedacych poprzednikami mojej krawedzi, wartosci
    @tailrec
    def orderEdges(done: List[((Int, Int), Int)] = Nil, todo: List[(Int, Int)] = gra): List[((Int, Int), Int)] = {
      if(todo.isEmpty) done
      else if(!done.exists(_._1._2 == todo.head._1)) orderEdges((todo.head, 0) :: done, todo.tail)
      else orderEdges((todo.head, done.filter(_._1._2 == todo.head._1).map(_._2).max+1) :: done, todo.tail)
    }

    // Grupujemy i przygotowujemy do wypisania
    val res: List[((Int, Int), Int)] = orderEdges().reverse
    data.Word.zipWithIndex.map(pair => {
      if(!res.exists(_._1._2 == pair._2)) (pair._1, 0)
      else (pair._1, res.filter(_._1._2 == pair._2).map(_._2).max+1)
    }).groupBy(_._2).toList.map(_._2.toList.map(_._1))
  }
}
