package kg.o.internlabs.omarket.data.remote


import kg.o.internlabs.omarket.data.remote.model.*
import kg.o.internlabs.omarket.data.remote.model.ads.AdsByCategoryDto
import kg.o.internlabs.omarket.data.remote.model.ads.AdsByFilterDto
import kg.o.internlabs.omarket.data.remote.model.ads.AdsDto
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @POST("api/market-auth/register/")
    suspend fun registerUser(
        @Body reg: RegisterDto?
    ): Response<RegisterDto?>

    @POST("/api/market-auth/check-otp/")
    suspend fun checkOtp(
        @Body otp: RegisterDto?
    ): Response<RegisterDto?>

    @POST("api/market-auth/refresh-token/")
    suspend fun refreshToken(
        @Body reg: RegisterDto?
    ): Response<RegisterDto?>

    @POST("api/market-auth/auth/msisdn-password/")
    suspend fun loginUser(
        @Body reg: RegisterDto?
    ): Response<RegisterDto?>

    @GET("api/ads-board/v1/category/list")
    suspend fun getCategories(
        @Header("Authorization") token: String?
    ): Response<CategoriesDto?>

    @GET("api/ads-board/faq/")
    suspend fun getFaq(
        @Header("Authorization") token: String?,
        @Query("page") page: Int?
    ): Response<FAQDto?>

    @POST("api/ads-board/v1/user/my-ads/")
    suspend fun getMyAds(
        @Header("Authorization") token: String?,
        @Body myAdsDto: MyAdsDto?,
        @Query("page") page: Int?
    ): Response<MyAdsDto?>

    @POST("api/ads-board/trusted/ads-filter-old/")
    suspend fun getAdsByCategory(
        @Header("Authorization") token: String?,
        @Body adsDto: AdsByCategoryDto?,
        @Query("page") page: Int?
    ): Response<AdsDto?>

    @POST("api/ads-board/trusted/ads-filter-old/")
    suspend fun getAdsByFilter(
        @Header("Authorization") token: String?,
        @Body adsDto: AdsByFilterDto?,
        @Query("page") page: Int?
    ): Response<AdsDto?>

    @Multipart
    @POST("api/ads-board/v1/user/upload-avatar/")
    suspend fun uploadAvatar(
        @Header("Authorization") token: String?,
        @Part image: MultipartBody.Part
    ): Response<UploadImageDto?>

    @DELETE("api/ads-board/v1/user/remove-avatar/")
    suspend fun deleteAvatar(
        @Header("Authorization") token: String?
    ): Response<DeleteDto?>

    @GET("/api/ads-board/v1/ads/list/")
    suspend fun getAds(
        @Header("Authorization") token: String?,
        @Query("page") page: Int
    ): Response<AdsDto?>

    @POST("api/ads-board/v1/ads/initial/")
    suspend fun initiateAd(
        @Header("Authorization") token: String?
    ): Response<InitiateAdDto?>

    @Multipart
    @POST("api/ads-board/v1/ads/{uuid}/upload-image/")
    suspend fun uploadImageToAd(
        @Header("Authorization") token: String?,
        @Part image: MultipartBody.Part,
        @Path("uuid") uuid: String
    ): Response<UploadImageDto?>

    @HTTP(method = "DELETE", path = "api/ads-board/v1/ads/{uuid}/remove-image/", hasBody = true)
    suspend fun deleteImageFromAd(
        @Header("Authorization") token: String?,
        @Body deletingImage: DeletedImageUrlDto,
        @Path("uuid") uuid: String
    ): Response<DeleteDto?>

    @PUT("api/ads-board/v1/ads/{uuid}/")
    suspend fun editAd(
        @Header("Authorization") token: String?,
        @Body uuid1: EditAdsDto,
        @Path("uuid") uuid: String
    ): Response<EditAdsDto?>

    @DELETE("api/ads-board/v1/ads/{uuid}/")
    suspend fun deleteAd(
        @Header("Authorization") token: String?,
        @Path("uuid") uuid: String
    ): Response<DeleteDto?>


    @GET("/api/ads-board/v1/ads/{uuid}/")
    suspend fun getDetailAd(
        @Header("Authorization") token: String?,
        @Path("uuid") uuid: String,
    ): Response<DetailsAdDto?>

    @GET("/api/ads-board/v1/ad_type/list/")
    suspend fun getAdType(
        @Header("Authorization") token: String?
    ): Response<AdTypeDto?>
}