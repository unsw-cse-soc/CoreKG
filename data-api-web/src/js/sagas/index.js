import { fork } from 'redux-saga/effects';
import * as AuthSagas from './AuthSagas';
import * as DatabaseSagas from './DatabaseSagas';
import * as EntitySagas from './EntitySagas';
import * as RelationSagas from './RelationSagas';

export default function* rootSaga() {
    yield [
        fork(AuthSagas.handlePostClientRequest),
        fork(AuthSagas.handlePostUserRequest),
        fork(AuthSagas.handleLoginRequest),
        fork(DatabaseSagas.handlePostDatabaseRequest),
        fork(DatabaseSagas.handleDeleteDatabaseRequest),
        fork(EntitySagas.handlePostEntityRequest),
        fork(RelationSagas.handlePostRelationRequest)
    ];
}