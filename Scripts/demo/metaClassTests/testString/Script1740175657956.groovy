/**
 * This script proves that java.lang.String is equipped with 
 * the metaClass in the Groovy runtime environment,
 * and you can inject a new method into String.
 */
String.metaClass.capitalize2 = { ->
	delegate.substring(0, 1).toUpperCase() + delegate.substring(1) + "!"
}

assert "norman".capitalize2() == "Norman!"
