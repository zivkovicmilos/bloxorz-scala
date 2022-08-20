package editor

abstract class EditOperation {
  // Runs the operation
  def run(): Unit

  // Returns the operation name
  def getName: String
}
