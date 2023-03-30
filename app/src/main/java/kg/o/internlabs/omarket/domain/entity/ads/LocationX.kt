package kg.o.internlabs.omarket.domain.entity.ads

data class LocationX(
    var id: Int? = null,
    var name: String? = null,
    var locationType: String? = null,
    var parent: Int? = null,
    val orderNum: Int? = null,
    val isPopular: Boolean? = null,
    val searchByName: String? = null,
    var subLocationsId: List<Int>? = null

)