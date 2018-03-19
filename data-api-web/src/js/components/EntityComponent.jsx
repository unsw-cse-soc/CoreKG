import React from "react";
import ReactDOM from "react-dom";
import RenderInput from './ui/RenderInput';
import RenderDropDown from './ui/RenderDropDown';
import RenderSubmitButton from './ui/RenderSubmitButton';
import { connect } from 'react-redux';
import { reduxForm, Field, formValueSelector, FieldArray } from 'redux-form';
import isNil from 'lodash/isNil';
import isEmpty from 'lodash/isEmpty';
import join from 'lodash/join';
import trimValue from '../services/TrimValue';
import { CREATE_DATABASE } from '../constants/ActionTypes';

class EntityComponent extends React.Component {

    constructor(props) {
        super(props);
    }

    componentDidMount() {
    }

    componentDidUpdate(prevProps) {
    }

    render() {
        const { handleCreateEntitySubmit, handleUpdateEntitySubmit, handleDeleteEntitySubmit, responses, done } = this.props;
        return <div class="row">
            <div class="col s12 m12 l12">
                <div class="card-panel teal lighten-2">Create an entity</div>
                <p class="caption">
                    CoreKG supports schema-less entities and it is not necessary to define schema for entities. It employs a solution in order to detect type of each attribute of an entity from the JSON object containing the entity. This allows you to manage inheritance straightforward without defining multiple tables and collection for each type
                </p>
                <CreateEntityForm onSubmit={handleCreateEntitySubmit} />
                {done && <p>Done!</p>}
            </div>
        </div>
    }
}

export default EntityComponent;

const renderAttributes = ({ fields, meta: { touched, error } }) => (
    <div class="row">
        {fields.map((attribute, index) =>
            <div class="col s12 m12 l12" key={index}>
                <Field name={`${attribute}.name`}
                    id={`attribute-name-input-${index}`}
                    label="Name"
                    component={RenderInput}
                    withContainer={false}
                    normalize={trimValue}
                    type="text" />
                <Field name={`${attribute}.value`}
                    id={`attribute-value-input-${index}`}
                    label="Value"
                    component={RenderInput}
                    withContainer={false}
                    type="text" />
            </div>
        )}
        <a class="btn-floating waves-effect waves-light blue" onClick={() => fields.push({})}><i class="material-icons">add</i></a>
    </div>
)

class CreateEntityForm extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        const {handleSubmit,
            submitting,
            databaseNameValue,
            datasetNameValue,
            entityTypeValue,
            attributeValues} = this.props;
        const attributeList = (isNil(attributeValues) || isEmpty(attributeValues)) ? ['"Param1":"Value1"', '"Param2": "Value2"'] : attributeValues.map(value => `"${value.name}":"${value.value}"`);
        return <div class="row">
            <div class="col s12 m12 l12">
                <div class="card blue-grey darken-1">
                    <div class="card-content white-text">
                        <pre>{`curl -H "Content-Type: application/json" -H "Authorization: Bearer ACCESS_TOKEN" -X POST -d '{${join(attributeList, ' ,')}}' http://CoreKG/api/entity/${(isNil(databaseNameValue) || isEmpty(databaseNameValue)) ? '{Database_NAME}' : databaseNameValue}/${(isNil(datasetNameValue) || isEmpty(datasetNameValue)) ? '{Dataset_NAME}' : datasetNameValue}/${(isNil(entityTypeValue) || isEmpty(entityTypeValue)) ? '{Entity_TYPE}' : entityTypeValue}`}</pre>
                    </div>
                </div>
            </div>
            <form class="col s12 m12 l12" onSubmit={handleSubmit}>
                <div class="row">
                    <Field name="databaseName"
                        id="database-name-input"
                        label="Database name"
                        component={RenderInput}
                        withContainer={false}
                        normalize={trimValue}
                        type="text" />
                    <Field name="datasetName"
                        id="dataset-name-input"
                        label="Dataset name"
                        component={RenderInput}
                        withContainer={false}
                        normalize={trimValue}
                        type="text" />
                    <Field name="entityType"
                        id="database-name-input"
                        label="Entity type"
                        component={RenderInput}
                        withContainer={false}
                        normalize={trimValue}
                        type="text" />
                    <div class="col s12 m12 l12">
                        <FieldArray name="attributes" component={renderAttributes} />
                    </div>
                </div>
                <div class="file-field input-field">
                    <div class="btn">
                        <span>File</span>
                        <input type="file" />
                    </div>
                    <div class="file-path-wrapper">
                        <input class="file-path validate" type="text" />
                    </div>
                </div>
                <RenderSubmitButton label="Submit" id="client-submit" name="client-submit" />
            </form>
        </div>
    }
}

CreateEntityForm = reduxForm({
    form: 'newEntityForm',
    validate: values => {
        const errors = {};
        return errors;
    }
})(CreateEntityForm);

const entityFormSelector = formValueSelector('newEntityForm');

CreateEntityForm = connect(
    state => {
        return {
            databaseNameValue: entityFormSelector(state, 'databaseName'),
            datasetNameValue: entityFormSelector(state, 'datasetName'),
            entityTypeValue: entityFormSelector(state, 'entityType'),
            attributeValues: entityFormSelector(state, 'attributes')
        }
    }
)(CreateEntityForm);

class DeleteDatabaseForm extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        const {handleSubmit, submitting } = this.props;
        return <div class="row">
            <div class="col s12 m12 l12">
                <div class="card blue-grey darken-1">
                    <div class="card-content white-text">
                        <pre>{`curl -H "Authorization: Bearer YOUR_ACCESS_TOKEN" -X DELETE http://CoreKG/api/databases/{DATABASE_NAME}`}</pre>
                    </div>
                </div>
            </div>
            <form class="col s12 m12 l12" onSubmit={handleSubmit}>
                <Field name="databaseName"
                    id="database-name-input"
                    label="Name"
                    component={RenderInput}
                    normalize={trimValue}
                    type="text" />
                <RenderSubmitButton label="Submit" id="client-submit" name="client-submit" />
            </form>
        </div>
    }
}

DeleteDatabaseForm = reduxForm({
    form: 'deleteDatabaseForm',
    validate: values => {
        const errors = {};
        return errors;
    }
})(DeleteDatabaseForm);