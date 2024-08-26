package org.lotka.xenonx.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.lotka.xenonx.data.repository.AuthRepositoryImpl
import org.lotka.xenonx.data.repository.ProfileRepositoryImpl
import org.lotka.xenonx.domain.repository.auth.AuthRepository
import org.lotka.xenonx.domain.repository.profile.ProfileRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideAuthRepository(
        firebaseAuth: FirebaseAuth
    ): AuthRepository = AuthRepositoryImpl(firebaseAuth)



}

    









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





