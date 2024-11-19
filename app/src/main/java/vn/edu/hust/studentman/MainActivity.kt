package vn.edu.hust.studentman

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {

  private lateinit var studentAdapter: StudentAdapter
  private val students = mutableListOf(
      StudentModel("Nguyễn Văn An", "SV001"),
      StudentModel("Trần Thị Bảo", "SV002"),
      StudentModel("Lê Hoàng Cường", "SV003"),
      StudentModel("Phạm Thị Dung", "SV004"),
      StudentModel("Đỗ Minh Đức", "SV005"),
      StudentModel("Vũ Thị Hoa", "SV006"),
      StudentModel("Hoàng Văn Hải", "SV007"),
      StudentModel("Bùi Thị Hạnh", "SV008"),
      StudentModel("Đinh Văn Hùng", "SV009"),
      StudentModel("Nguyễn Thị Linh", "SV010"),
      StudentModel("Phạm Văn Long", "SV011"),
      StudentModel("Trần Thị Mai", "SV012"),
      StudentModel("Lê Thị Ngọc", "SV013"),
      StudentModel("Vũ Văn Nam", "SV014"),
      StudentModel("Hoàng Thị Phương", "SV015"),
      StudentModel("Đỗ Văn Quân", "SV016"),
      StudentModel("Nguyễn Thị Thu", "SV017"),
      StudentModel("Trần Văn Tài", "SV018"),
      StudentModel("Phạm Thị Tuyết", "SV019"),
      StudentModel("Lê Văn Vũ", "SV020")
    )
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    studentAdapter = StudentAdapter(
      students,
      onEdit = { position -> showEditStudentDialog(position) },
      onDelete = { position -> showDeleteStudentDialog(position) }
    )
    findViewById<RecyclerView>(R.id.recycler_view_students).run {
      adapter = studentAdapter
      layoutManager = LinearLayoutManager(this@MainActivity)
    }

    findViewById<Button>(R.id.btn_add_new).setOnClickListener {
      showAddStudentDialog()
    }
  }
   private fun showAddStudentDialog() {
      val dialogView = layoutInflater.inflate(R.layout.dialog_add_student, null)
      val nameField = dialogView.findViewById<EditText>(R.id.etName)
      val idField = dialogView.findViewById<EditText>(R.id.etId)

      AlertDialog.Builder(this)
        .setView(dialogView)
        .setTitle("Add new student")
        .setPositiveButton("Add") { _, _ ->
          val name = nameField.text.toString()
          val id = idField.text.toString()
          if (name.isNotBlank() && id.isNotBlank()) {
            students.add(StudentModel(name,id))
            studentAdapter.notifyItemInserted(students.size-1)
          }
        }
        .setNegativeButton("Cancel", null)
        .create()
        .show()
    }
   private fun showEditStudentDialog(position: Int) {
      val student = students[position]
      val dialogView = layoutInflater.inflate(R.layout.dialog_add_student, null)
      val nameField = dialogView.findViewById<EditText>(R.id.etName)
      val idField = dialogView.findViewById<EditText>(R.id.etId)
      nameField.setText(student.studentName)
      idField.setText(student.studentId)

      AlertDialog.Builder(this)
        .setView(dialogView)
        .setTitle("Edit student")
        .setPositiveButton("Save") { _, _ ->
          val updatedName = nameField.text.toString()
          val updatedId = idField.text.toString()
          if(updatedName.isNotBlank() && updatedId.isNotBlank()) {
            students[position] = StudentModel(updatedName, updatedId)
            studentAdapter.notifyItemChanged(position)
          }
        }
        .setNegativeButton("Cancel", null)
        .create()
        .show()
    }
    private fun showDeleteStudentDialog(position: Int) {
      val student = students[position]
      AlertDialog.Builder(this)
        .setTitle("Delete student")
        .setMessage("Are you sure you want to delete ${student.studentName}?")
        .setPositiveButton("Delete") { _, _ ->
          val removedStudent = students.removeAt(position)
          studentAdapter.notifyItemRemoved(position)
          Snackbar.make(findViewById(android.R.id.content), "${removedStudent.studentName} deleted", Snackbar.LENGTH_LONG)
            .setAction("Undo") {
              students.add(position, removedStudent)
              studentAdapter.notifyItemInserted(position)
            }
              .show()
        }
        .setNegativeButton("Cancel", null)
        .create()
        .show()
    }
    }