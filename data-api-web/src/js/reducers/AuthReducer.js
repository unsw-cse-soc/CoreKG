import isNil from 'lodash/isNil';
import Immutable from 'immutable';
import User from '../models/User';
import UserMap from '../models/UserMap';
import Client from '../models/Client';
import ClientMap from '../models/ClientMap';
import ResponseMap from '../models/ResponseMap';
import Response from '../models/Response';
import Token from '../models/Token';
import * as ActionTypes from '../constants/ActionTypes';

const initState = Immutable.Map({
    token: new Token({
        accessToken: isNil(sessionStorage.getItem('auth')) ? null : JSON.parse(sessionStorage.getItem('auth')).accessToken,
        refreshToken: isNil(sessionStorage.getItem('auth')) ? null : JSON.parse(sessionStorage.getItem('auth')).refreshToken,
        expiresIn: isNil(sessionStorage.getItem('auth')) ? null : JSON.parse(sessionStorage.getItem('auth')).expiresIn,
    }),
    fetching: false,
    fetched: false,
    error: null,
    users: new UserMap(),
    clients: new ClientMap(),
    responses: new ResponseMap(),
    //represents the logged user
    user: new User({
        id: isNil(sessionStorage.getItem('auth')) ? null : JSON.parse(JSON.parse(sessionStorage.getItem('auth')).user).id,
        userName: isNil(sessionStorage.getItem('auth')) ? '' : JSON.parse(JSON.parse(sessionStorage.getItem('auth')).user).userName,
        role: isNil(sessionStorage.getItem('auth')) ? '' : JSON.parse(JSON.parse(sessionStorage.getItem('auth')).user).role
    })
})

const mergeUsers = (state, newUsers) =>
    state.get('users').merge(newUsers.map((user) => new User(user)));

const mergeClients = (state, newClients) =>
    state.get('clients').merge(newClients.map((client) => new Client(client)));

export default function reducer(state = initState, action) {
    switch (action.type) {
        case ActionTypes.CREATE_CLIENT:
        case ActionTypes.CREATE_USER:
        case ActionTypes.REQUEST_TOKEN: {
            return state.withMutations(map => {
                map.set('fetching', true)
                    .set('fetched', false)
                    .set('error', null)
                    .deleteIn(['responses', action.type]);
            });
        }
        case ActionTypes.CREATE_CLIENT_FULFILLED: {
            if (action.payload.entities.clients) {
                return state.withMutations(map => {
                    map.set('fetching', false)
                        .set('fetched', true)
                        .set('clients', mergeClients(state, Immutable.fromJS(action.payload.entities.clients)))
                        .setIn(['responses', ActionTypes.CREATE_CLIENT], new Response({ body: action.payload.entities.clients[Object.keys(action.payload.entities.clients)[0]] }));
                });
            } else {
                return state.withMutations(map => {
                    map.set('fetching', false)
                        .set('fetched', true)
                });
            }
        }
        case ActionTypes.CREATE_USER_FULFILLED: {
            if (action.payload.entities.users) {
                return state.withMutations(map => {
                    map.set('fetching', false)
                        .set('fetched', true)
                        .set('users', mergeUsers(state, Immutable.fromJS(action.payload.entities.users)))
                        .setIn(['responses', ActionTypes.CREATE_USER], new Response({ body: action.payload.entities.users[Object.keys(action.payload.entities.users)[0]] }));
                });
            } else {
                return state.withMutations(map => {
                    map.set('fetching', false)
                        .set('fetched', true)
                });
            }
        }
        case ActionTypes.REQUEST_TOKEN_FULFILLED: {
            sessionStorage.clear();
            sessionStorage.setItem('auth', JSON.stringify({ accessToken: action.payload.access_token, refreshToken: action.payload.refresh_token, expiresIn: action.payload.expires_in, user: action.payload.user }));
            return state.withMutations(map => {
                map.set('fetching', false)
                    .set('fetched', true)
                    .setIn(['responses', ActionTypes.REQUEST_TOKEN], new Response({ body: { accessToken: action.payload.access_token, refreshToken: action.payload.refresh_token, expiresIn: action.payload.expires_in } }))
                    .set('user', new User(JSON.parse(action.payload.user)))
                    .set('token', new Token({
                        accessToken: action.payload.access_token,
                        refreshToken: action.payload.refresh_token,
                        expiresIn: action.payload.expires_in
                    }));
            });
        }
        case ActionTypes.CREATE_CLIENT_REJECTED:
        case ActionTypes.CREATE_USER_REJECTED:
        case ActionTypes.REQUEST_TOKEN_REJECTED: {
            return state.withMutations(map => {
                map.set('fetching', false)
                    .set('fetched', false)
                    .set('error', action.payload);
            });
        }
        case ActionTypes.CLEAR_AUTH_RESPONSES: {
            return state.set('responses', ResponseMap());
        }
    }
    return state;
}