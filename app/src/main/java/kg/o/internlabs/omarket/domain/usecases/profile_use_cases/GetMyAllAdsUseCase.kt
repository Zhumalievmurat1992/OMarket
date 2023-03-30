package kg.o.internlabs.omarket.domain.usecases.profile_use_cases

import kg.o.internlabs.omarket.domain.entity.MyAdsEntity
import kg.o.internlabs.omarket.domain.repository.ProfileRepository
import javax.inject.Inject

class GetMyAllAdsUseCase @Inject constructor(
    private val profileRep: ProfileRepository
) {
    private var myAdsEntity = MyAdsEntity(
        statuses = listOf("active","cancelled", "moderate", "disabled")
    )

    operator fun invoke(token: String) =
        profileRep.getMyAllAds(token, myAdsEntity)

}