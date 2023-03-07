package traces

class Data(val name: String) {
  // Wczytujemy wszystkie linie z pliku o danej nazwie
  val lines: List[String] = readFile(name)


  // Wyznaczamy zadany alfabet
  // sposrod wczytanych linii wyszukuje zaczynajaca sie od "A", i pomijam wszystkie znaki spoza a-z
  lazy val Alphabet: List[Char] = {
    lines.filter(_.startsWith("A")).mkString("").replaceAll("[^a-z]", "").toList
  }


  // Wyznaczamy zadane slowo
  // sposrod wczytanych linii wyszukuje zaczynajaca sie od "w", i pomijam wszystko do "=" wlacznie
  lazy val Word: String = {
    lines.filter(_.startsWith("w")).mkString("").dropWhile(_ != '=').tail.trim()
  }


  // Wyznaczamy produkcje
  // sposrod wczytanych linii wyszukuje zaczynajace sie od "(", i pomijam wszystkie znaki spoza a-z
  // otrzymuje krotke (litera z alfabetu, zmienna przed "=", zmienna po +, druga zmienna po +) - zakladam poprawnosc
  lazy val Prods: List[(Char, Char, Char, Char)] ={
    lines.filter(_.startsWith("(")).map(
      line => {
        val simp = line.replaceAll("[^a-z]", "")
        (simp(0), simp(1), simp(2), simp(3))
      }
    )
  }


  // Funkcja czytajaca wszystkie linie z pliku, w sposob bezpieczny
  def readFile(name: String): List[String] = try{
    val file = scala.io.Source.fromFile(name)
    val lines = try {
      file.getLines.toList
    }
    finally {
      file.close
    }
    lines
  } catch {
    case _: java.io.FileNotFoundException => println("File not found " + name); List()
    case ex: Throwable => println("Exception " + ex.getMessage); List()
  }
}
