package org.lotka.xenonx.di

import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.lotka.xenonx.data.repository.AuthRepositoryImpl
import org.lotka.xenonx.domain.repository.auth.AuthRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideAuthRepository(
        firebaseAuth: FirebaseAuth
    ): AuthRepository = AuthRepositoryImpl(firebaseAuth)



    









//    when we have api call we need to provide this below code

//    @Provides
//    @Singleton
//    fun provideJwtToken(sharedPreferences: SharedPreferences):String{
//        return sharedPreferences.getString(JWT_TOKEN, "").toString()
//    }
//
//    @Provides
//    @Singleton
//    fun provideOkHttpClient(token: String): OkHttpClient {
//        return OkHttpClient.Builder()
//            .addInterceptor {
//                val request = it.request().newBuilder()
//                    .addHeader("Authorization", "Bearer $token")
//                    .build()
//                     it.proceed(request)
//            }
//            .build()
//
//    }

}



