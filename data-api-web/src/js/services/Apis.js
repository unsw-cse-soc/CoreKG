import request from "superagent";

export function Fetch(url, accessToken) {
    return request
        .get(url)
        .set('Authorization', 'Bearer ' + accessToken);
}

export function FetchWithParam(url, accessToken, params) {
    return request
        .get(url)
        .set('Authorization', 'Bearer ' + accessToken)
        .query(params);
}

export function Post(url, params, accessToken) {
    return request
        .post(url)
        .set('Authorization', 'Bearer ' + accessToken)
        .send(params);
}

export function Put(url, accessToken, params) {
    return request
        .put(url)
        .set('Authorization', 'Bearer ' + accessToken)
        .send(params);
}

export function Form(url, params) {
    return request
        .post(url)
        .type('form')
        .send(params);
}

export function FormData(url, accessToken, image) {
    return request
        .post(url)
        .set('Authorization', 'Bearer ' + accessToken)
        .attach('file', image);
}