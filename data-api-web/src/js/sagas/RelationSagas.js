import * as ActionTypes from '../constants/ActionTypes';
import { take, put, call } from 'redux-saga/effects';
import * as Api from '../services/Apis';
import * as EntityActions from '../actions/EntityActions';
import { normalize } from 'normalizr';
import schema from '../schemas';

export function* handlePostRelationRequest() {
    // run the daemon
    while (true) {
        try {
            // wait for a create relation request
            const {payload} = yield take(ActionTypes.CREATE_RELATION);
            const relation = {
                sourceDatabase: payload.sourceDatabase,
                sourceDataset: payload.sourceDataset,
                source: payload.sourceId,
                destinationDatabase: payload.destinationDatabase,
                destinationDataset: payload.destinationDataset,
                destination: payload.destinationId,
                name: payload.relationName
            };
            // call the api
            const data = yield call(Api.Post, `/api/relations/${payload.sourceType}/${payload.destinationType}`, relation, payload.accessToken);
            // call the success
            console.log(data);
            //yield put(EntityActions.createDatabaseFulfilled(normalize(data.body, schema.database)));
        } catch (e) {
            // call the error
            //yield put(EntityActions.createDatabaseRejected(e));
        }
    }
}