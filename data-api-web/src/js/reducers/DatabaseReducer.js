import isNil from 'lodash/isNil';
import Immutable from 'immutable';
import Database from '../models/Database';
import DatabaseMap from '../models/DatabaseMap';
import Response from '../models/Response';
import ResponseMap from '../models/ResponseMap';
import * as ActionTypes from '../constants/ActionTypes';

const mergeDatabases = (state, newDatabases) =>
    state.get('databases').merge(newDatabases.map((database) => new Database(database)));

export default function reducer(state = Immutable.Map({
    fetching: false,
    fetched: false,
    error: null,
    databases: new DatabaseMap(),
    responses: new ResponseMap(),
}), action) {
    switch (action.type) {
        case ActionTypes.CREATE_DATABASE:
        case ActionTypes.DELETE_DATABASE: {
            return state.withMutations(map => {
                map.set('fetching', true)
                    .set('fetched', false)
                    .set('error', null)
                    .deleteIn(['responses', action.type]);
            });
        }
        case ActionTypes.CREATE_DATABASE_FULFILLED: {
            if (action.payload.entities.databases) {
                return state.withMutations(map => {
                    map.set('fetching', false)
                        .set('fetched', true)
                        .set('databases', mergeDatabases(state, Immutable.fromJS(action.payload.entities.databases)))
                        .setIn(['responses', ActionTypes.CREATE_DATABASE], new Response({ body: action.payload.entities.databases[Object.keys(action.payload.entities.databases)[0]] }));
                });
            } else {
                return state.withMutations(map => {
                    map.set('fetching', false)
                        .set('fetched', true)
                });
            }
        }
        case ActionTypes.CREATE_DATABASE_REJECTED:
        case ActionTypes.DELETE_DATABASE_REJECTED: {
            return state.withMutations(map => {
                map.set('fetching', false)
                    .set('fetched', false)
                    .set('error', action.payload);
            });
        }
        case ActionTypes.CLEAR_DATABASE_RESPONSES: {
            return state.set('responses', ResponseMap());
        }
    }
    return state;
}