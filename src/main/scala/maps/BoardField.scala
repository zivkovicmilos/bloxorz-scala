package maps

import maps.FieldType.FieldType

// Represents a single board field
case class BoardField(fieldType: FieldType) {
  def getBoardField: Array[Array[String]] = {
    Array(
      Array("+", "-", "-", "-", "+"),
      Array("+", " ", FieldType.getValue(fieldType), " ", "+"),
      Array("+", "-", "-", "-", "+"),
    )
  }
}
