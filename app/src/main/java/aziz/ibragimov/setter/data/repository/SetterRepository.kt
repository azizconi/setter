package azizjon.ibragimov.setter.data.repository

import azizjon.ibragimov.setter.data.ContentResolverHelper
import aziz.ibragimov.setter.data.local.music.Music
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SetterRepository @Inject constructor(private val contentResolverHelper: ContentResolverHelper) {
    suspend fun getAudioData(): List<Music> = withContext(Dispatchers.IO){
        contentResolverHelper.getAudioData()
    }

}