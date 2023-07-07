import * as Api from './api_service'

const _prepareApiObj = (method, endpoint, body, state) => {
    const apiObject = {}
    apiObject.method = method
    apiObject.endPoint = endpoint
    apiObject.body = body
    apiObject.state = state
    return apiObject
}

export async function login(body) {
    return await Api.callApi(
        _prepareApiObj('post', '/user/login', body)
    )
}

export async function createPost(body) {
    return await Api.callApi(
        _prepareApiObj('post', '/post/create', body, 'form')
    )
}

export async function updatePost(body) {
    return await Api.callApi(
        _prepareApiObj('put', '/post', body, 'form')
    )
}

export async function getAllPosts() {
    return await Api.callApi(
        _prepareApiObj('get', '/post', null)
    )
}

export async function getPostsByUserId(userId) {
    return await Api.callApi(
        _prepareApiObj('get',   `/post/user/${userId}`, null)
    )
}

export async function getPostsByPostId(postId) {
    return await Api.callApi(
        _prepareApiObj('get',   `/post/${postId}`, null)
    )
}

export async function manageLikes(postId, userId) {
    return await Api.callApi(
        _prepareApiObj('post',   `/like/post/${postId}/user/${userId}`, null)
    )
}

export async function addComment(body) {
    return await Api.callApi(
        _prepareApiObj('post',   '/comment', body)
    )
}

export async function getCommentsByPostId(postId) {
    return await Api.callApi(
        _prepareApiObj('get',   `/comment/post/${postId}`, null)
    )
}

export async function deletePost(postId) {
    return await Api.callApi(
        _prepareApiObj('delete',   `/post/delete/${postId}`, null)
    )
}
