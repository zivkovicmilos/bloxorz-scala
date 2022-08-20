package maps

import maps.FieldType.FieldType

// Represents a single board field
case class BoardField(fieldType: FieldType, position: Position) {
  def getBoardField: Array[Array[String]] = {
    val color = FieldType.getColor(fieldType)

    Array(
      Array(color + "+", color + "-", color + "-", color + "-", color + "+"),
      Array(color + "|", " ", color + FieldType.getValue(fieldType), " ", color + "|"),
      Array(color + "+", color + "-", color + "-", color + "-", color + "+"),
    )
  }

  def getPosition: Position = {
    position
  }
}
