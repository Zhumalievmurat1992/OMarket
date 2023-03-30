package kg.o.internlabs.omarket.presentation.ui.fragments.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.loader.content.CursorLoader
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kg.o.internlabs.core.base.BaseFragment
import kg.o.internlabs.core.common.ApiState
import kg.o.internlabs.core.custom_views.cells.cells_utils.CustomProfileCellViewClickers
import kg.o.internlabs.omarket.R
import kg.o.internlabs.omarket.databinding.FragmentProfileBinding
import kg.o.internlabs.omarket.domain.entity.MyAdsResultsEntity
import kg.o.internlabs.omarket.presentation.ui.fragments.main.MarginItemDecoration
import kg.o.internlabs.omarket.presentation.ui.fragments.profile.adapter.AdClicked
import kg.o.internlabs.omarket.presentation.ui.fragments.profile.adapter.AdsPagingAdapter
import kg.o.internlabs.omarket.utils.*
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>(),
    CustomProfileCellViewClickers, AdClicked {

    private var args: ProfileFragmentArgs? = null
    private var adapter = AdsPagingAdapter()
    private var isActive = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        args = ProfileFragmentArgs.fromBundle(requireArguments())
    }

    override fun onStart() {
        super.onStart()
        binding.btnActive.isChecked = true
        getActiveAds()
        isActive = true
    }

    override val viewModel: ProfileViewModel by lazy {
        ViewModelProvider(this)[ProfileViewModel::class.java]
    }

    override fun inflateViewBinding(inflater: LayoutInflater) =
        FragmentProfileBinding.inflate(inflater)

    override fun initViewModel() {
        super.initViewModel()
        binding.cusProfile.setInterface(this@ProfileFragment)
    }

    override fun initView() {
        super.initView()
        args?.number?.let { binding.cusProfile.setTitle(it) }
        adapter.setInterface(this@ProfileFragment, this)
        initAdapter()
        setImageToAvatar()
        loadAllAds()
        getMenu()
        visibleStatusBar()
    }

    override fun initListener() = with(binding) {
        super.initListener()
        btnActive.setOnClickListener {
            getActiveAds()
            isActive = true
        }
        btnNonActive.setOnClickListener {
            getNonActiveAds()
            isActive = false
        }
        tbProfile.setNavigationOnClickListener { findNavController().navigateUp() }
    }

    private fun visibleStatusBar() {
        WindowInsetsControllerCompat(
            requireActivity().window,
            requireView()
        ).show((WindowInsetsCompat.Type.statusBars()))
    }

    private fun initAdapter() = with(binding) {
        rec.adapter = adapter.withLoadStateHeaderAndFooter(
            header = LoaderStateAdapter(),
            footer = LoaderStateAdapter()
        )
        rec.addItemDecoration(
            MarginItemDecoration(2, resources.getDimensionPixelSize(R.dimen.item_margin_7dp), true))
        loadListener(adapter, prog, rec)
    }

    private fun openSomeActivityForResult() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        resultLauncher.launch(Intent.createChooser(intent, "image"))
    }

    private var resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(), fun(result: ActivityResult) {
            if (result.resultCode != AppCompatActivity.RESULT_OK) return
            val intent: Intent? = result.data
            if (intent?.data == null) return
            val imageUri: Uri = intent.data!!
            uploadImage(imageUri)
        }
    )

    private fun uploadImage(imageUri: Uri) {
        checkPermission()
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val loader = CursorLoader(
            requireActivity(), imageUri, proj,
            null, null, null
        )
        viewModel.uploadAvatar(loader)
        setAvatar()
    }

    private fun setImageToAvatar() {
        safeFlowGather {
            viewModel.avatarUrl.collectLatest {
                binding.cusProfile.setIcon(it)
            }
        }
    }

    private fun loadAllAds() = with(binding) {
        safeFlowGather {
            viewModel.allAds.collectLatest {
                when (it) {
                    is ApiState.Success -> {
                        val count = it.data.result?.count
                        if (count != null) {
                            btnNonActive.isVisible = count > 0
                            btnActive.isVisible = count > 0
                        }
                    }
                    is ApiState.Failure -> {
                        btnNonActive.isVisible = false
                        btnActive.isVisible = false
                    }
                    is ApiState.Loading -> {
                        btnNonActive.isVisible = false
                        btnActive.isVisible = false
                    }
                }
            }
        }
    }

    private fun setAvatar() = with(binding) {
        safeFlowGather {
            viewModel.avatar.collectLatest {
                when (it) {
                    is ApiState.Success -> {
                        cusProfile.isProgressVisible()
                        val uri = it.data.result?.url.toString()
                        cusProfile.setIcon(uri)
                    }
                    is ApiState.Failure -> {
                        cusProfile.isProgressVisible()
                        requireActivity().makeToast(it.msg.message.toString())
                    }
                    is ApiState.Loading -> {
                        cusProfile.isProgressVisible(true)
                    }
                }
            }
        }
    }


    private fun getNonActiveAds() {
        safeFlowGather {
            viewModel.nonActiveAds.collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun getActiveAds() {
        safeFlowGather {
            viewModel.activeAds.collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun getMenu() {
        binding.tbProfile.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_my_profile, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.menu_faq_button -> {
                        findNavController().navigate(R.id.FAQFragment)
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun iconClick() {
        openSomeActivityForResult()
    }

    override fun iconLongClick() = with(binding) {
        viewModel.deleteAvatar()
        safeFlowGather {
            viewModel.deleteAvatar.collectLatest {
                when (it) {
                    is ApiState.Success -> {
                        cusProfile.isProgressVisible()
                        cusProfile.setIcon(kg.o.internlabs.core.R.drawable.ic_user_profile)
                    }
                    is ApiState.Failure -> {
                        cusProfile.isProgressVisible()
                        requireActivity().makeToast(it.msg.message.toString())
                    }
                    is ApiState.Loading -> {
                        cusProfile.isProgressVisible(true)
                    }
                }
            }
        }
    }

    override fun adClicked(ad: MyAdsResultsEntity) {
        if (isActive) findNavController().navigate(ProfileFragmentDirections.goToMyAds("true"+ad.uuid.toString()))
        else findNavController().navigate(ProfileFragmentDirections.goToEditFragment(ad.uuid))
    }
}