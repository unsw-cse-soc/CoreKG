import * as ActionTypes from '../constants/ActionTypes';
import { take, put, call } from 'redux-saga/effects';
import * as Api from '../services/Apis';
import * as EntityActions from '../actions/EntityActions';
import { normalize } from 'normalizr';
import schema from '../schemas';

export function* handlePostEntityRequest() {
    // run the daemon
    while (true) {
        try {
            // wait for a create entity request
            const {payload} = yield take(ActionTypes.CREATE_ENTITY);
            // const entity = {};
            // payload.attributes.forEach((item) => {
            //     entity[item.name] = item.value;
            // });
            // call the api
           // const data = yield call(Api.Post, `/api/entities/${payload.databaseName}/${payload.datasetName}/${payload.entityType}`, entity, payload.accessToken);
            // call the success
           // console.log(data);
            yield put(EntityActions.createEntityFulfilled(""));
        } catch (e) {
            // call the error
            //yield put(EntityActions.createDatabaseRejected(e));
        }
    }
}