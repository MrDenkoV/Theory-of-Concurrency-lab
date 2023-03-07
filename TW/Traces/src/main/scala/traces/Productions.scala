package traces

class Productions(val prods: List[(Char,Char,Char,Char)]) {
  // Wyznaczamy zbior D (zaleznosci), wykorzystujac liste prod z obiektu Data
  // Tworze produkt kartezjanski i wybieram tylko te pary, ktorych elementy sa w relacji
  lazy val D: Set[(Char, Char)] = {
    prods.flatMap(prodA => {
      prods.map(prodB => (prodA, prodB))
    }).filter(rel => ArelB(rel._1, rel._2)).map(rel => {
      (rel._1._1, rel._2._1)
    }).toSet
  }

  // Wyznaczamy zbior I (niezaleznosci), wykorzystujac liste prod z obiektu Data
  // tworze produkt kartezjanski i wybieram tylko te pary, ktorych elementy nie sa w relacji
  lazy val I: Set[(Char, Char)] = {
    prods.flatMap(prodA => {
      prods.map(prodB => (prodA, prodB))
    }).filterNot(rel => ArelB(rel._1, rel._2)).map(rel => {
      (rel._1._1, rel._2._1)
    }).toSet
  }


  // Sprawdzam czy dwa elementy z prods z obiektu Data, sa w relacji
  def ArelB(A: (Char, Char, Char, Char), B: (Char, Char, Char, Char)):Boolean ={
    A._2 == B._3 || A._2 == B._4 || B._2 == A._3 || B._2 == A._4
  }
}
