package du.ducs.thoughtboard

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.fragment.app.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import du.ducs.thoughtboard.databinding.FragmentNewMessageBinding

class NewMessageFragment : Fragment() {
    // The view binding to access views.
    private var _binding: FragmentNewMessageBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MessageViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewMessageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity: AppCompatActivity = (activity as AppCompatActivity?)!!

        activity.setSupportActionBar(binding.toolbar)
        setHasOptionsMenu(true)

        activity.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        activity.supportActionBar!!.setDisplayShowTitleEnabled(false)
        activity.supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_drawable_back)
        activity.supportActionBar!!.title = ""

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_new_message, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            //onClick for back
            android.R.id.home ->{
                navigateBack()
            }

            //onClick for send
            R.id.send -> {
                val title   = binding.titleEditText.text.toString()
                val message = binding.newMessageEditText.text.toString()

                if(title.isNotBlank() && message.isNotBlank()) {
                    MaterialAlertDialogBuilder((activity as AppCompatActivity?)!!)
                        .setTitle(R.string.confirm_send_dialog_msg)
                        .setCancelable(true)
                        .setPositiveButton("Yes") {
                                _, _ ->
                            run {
                                viewModel.sendMessage(title, message)
                                navigateBack()
                            }
                        }
                        .show()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun navigateBack(){
        findNavController().navigate(R.id.action_newMessageFragment_to_homeScreenFragment)
    }
}