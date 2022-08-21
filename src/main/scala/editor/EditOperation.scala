package editor

// Arbitrary edit operation that can be performed during
// map edits
abstract class EditOperation {
  // Runs the operation
  def run(): Unit

  // Returns the operation name
  def getName: String
}
