package ru.netology.nmedia.repository

//
//class PostRepositoryRoomImpl(
//    private val dao: PostDao,
//) : PostRepository {
//
//    override fun getAll() = Transformations.map(dao.getAll()){list ->
//        list.map {
//            it.toDto()
//        }
//
//    }
//
//    override fun save(post: Post) {
//        dao.save(PostEntity.fromDto(post))
//    }
//
//    override fun likeById(id: Long) {
//        dao.likeById(id)
//    }
//
//    override fun times() {
//
//    }
//
//    override fun repostById(id: Long) {
//        dao.repostById(id)
//    }
//
//    override fun removeById(id: Long) {
//        dao.removeById(id)
//    }
//}