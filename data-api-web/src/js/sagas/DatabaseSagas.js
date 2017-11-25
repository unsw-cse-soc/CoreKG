import * as ActionTypes from '../constants/ActionTypes';
import { take, put, call } from 'redux-saga/effects';
import * as Api from '../services/Apis';
import * as DatabaseActions from '../actions/DatabaseActions';
import { normalize } from 'normalizr';
import schema from '../schemas';

export function* handlePostDatabaseRequest() {
    // run the daemon
    while (true) {
        try {
            // wait for a create database request
            const {payload} = yield take(ActionTypes.CREATE_DATABASE);
            // call the api
            const data = yield call(Api.Post, '/api/databases', {
                name: payload.name,
                type: payload.databaseType
            }, payload.accessToken);
            // call the success
            yield put(DatabaseActions.createDatabaseFulfilled(normalize(data.body, schema.database)));
        } catch (e) {
            // call the error
            yield put(DatabaseActions.createDatabaseRejected(e));
        }
    }
}

export function* handleDeleteDatabaseRequest() {
    // run the daemon
    while (true) {
        try {
            // wait for a delete database request
            const {payload} = yield take(ActionTypes.DELETE_DATABASE);
            // call the api
            const data = yield call(Api.Post, '/api/account/register', {
                name: payload.name,
            }, payload.accessToken);
            // call the success
            yield put(DatabaseActions.deleteDatabaseFulfilled());
        } catch (e) {
            // call the error
            yield put(DatabaseActions.deleteDatabaseRejected(e));
        }
    }
}