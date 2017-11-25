import React from "react";
import ReactDOM from "react-dom";
import RenderInput from './ui/RenderInput';
import RenderSubmitButton from './ui/RenderSubmitButton';
import { connect } from 'react-redux';
import { reduxForm, Field, formValueSelector } from 'redux-form';
import isNil from 'lodash/isNil';
import isEmpty from 'lodash/isEmpty';
import join from 'lodash/join';
import trimValue from '../services/TrimValue';

class RelationComponent extends React.Component {

    constructor(props) {
        super(props);
    }

    componentDidMount() {
    }

    componentDidUpdate(prevProps) {
    }

    render() {
        const { handleCreateRelationSubmit, responses } = this.props;
        return <div class="row">
            <div class="col s12 m12 l12">
                <div class="card-panel teal lighten-2">Create a relation</div>
                <p class="caption">
                    Coredb supports schema-less entities and it is not necessary to define schema for entities. It employs a solution in order to detect type of each attribute of an entity from the JSON object containing the entity. This allows you to manage inheritance straightforward without defining multiple tables and collection for each type
                </p>
                <CreateRelationForm onSubmit={handleCreateRelationSubmit} />
            </div>
        </div>
    }
}

export default RelationComponent;

class CreateRelationForm extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        const { handleSubmit,
            submitting,
            sourceDatabaseNameValue,
            sourceDatasetNameValue,
            sourceEntityTypeValue,
            sourceEntityIdValues,
            destinationDatabaseNameValue,
            destinationDatasetNameValue,
            destinationEntityTypeValue,
            destinationEntityIdValues,
            relationNameValues } = this.props;
        return <div class="row">
            <div class="col s12 m12 l12">
                <div class="card blue-grey darken-1">
                    <div class="card-content white-text">
                        <pre>{`curl -H "Content-Type: application/json" -H "Authorization: Bearer ACCESS_TOKEN" -X POST -d '{"sourceDatabase":"${(isNil(sourceDatabaseNameValue) || isEmpty(sourceDatabaseNameValue)) ? "SOURCE_DATABASE" : sourceDatabaseNameValue}", "sourceDataset":"${(isNil(sourceDatasetNameValue) || isEmpty(sourceDatasetNameValue)) ? "SOURCE_DATASET" : sourceDatasetNameValue}"}' http://CoreDB/api/relations/${(isNil(sourceEntityTypeValue) || isEmpty(sourceEntityTypeValue)) ? '{SOURCE_TYPE}' : sourceEntityTypeValue}/${(isNil(destinationEntityTypeValue) || isEmpty(destinationEntityTypeValue)) ? '{DESTINATION_TYEP}' : destinationEntityTypeValue}`}</pre>
                    </div>
                </div>
            </div>
            <form class="col s12 m12 l12" onSubmit={handleSubmit}>
                <div class="row">
                    <Field name="sourceDatabaseName"
                        id="source-database-name-input"
                        label="Source database name"
                        component={RenderInput}
                        withContainer={false}
                        normalize={trimValue}
                        type="text" />
                    <Field name="sourceDatasetName"
                        id="source-dataset-name-input"
                        label="Source dataset name"
                        component={RenderInput}
                        withContainer={false}
                        normalize={trimValue}
                        type="text" />
                    <Field name="sourceEntityType"
                        id="source-entity-type-input"
                        label="Source entity type"
                        component={RenderInput}
                        withContainer={false}
                        normalize={trimValue}
                        type="text" />
                    <Field name="sourceEntityId"
                        id="source-entity-id-input"
                        label="Source entity id"
                        component={RenderInput}
                        withContainer={false}
                        normalize={trimValue}
                        type="text" />
                    <Field name="destinationDatabaseName"
                        id="destination-database-name-input"
                        label="Destination database name"
                        component={RenderInput}
                        withContainer={false}
                        normalize={trimValue}
                        type="text" />
                    <Field name="destinationDatasetName"
                        id="destination-dataset-name-input"
                        label="Destination dataset name"
                        component={RenderInput}
                        withContainer={false}
                        normalize={trimValue}
                        type="text" />
                    <Field name="destinationEntityType"
                        id="destination-entity-type-input"
                        label="Destination entity type"
                        component={RenderInput}
                        withContainer={false}
                        normalize={trimValue}
                        type="text" />
                    <Field name="destinationEntityId"
                        id="destination-entity-id-input"
                        label="Destination entity id"
                        component={RenderInput}
                        withContainer={false}
                        normalize={trimValue}
                        type="text" />
                    <Field name="relationName"
                        id="relation-name-input"
                        label="Relation name"
                        component={RenderInput}
                        withContainer={false}
                        normalize={trimValue}
                        type="text" />
                </div>
                <RenderSubmitButton label="Submit" id="relation-submit" name="relation-submit" />
            </form>
        </div>
    }
}

CreateRelationForm = reduxForm({
    form: 'newRelationForm',
    validate: values => {
        const errors = {};
        return errors;
    }
})(CreateRelationForm);

const relationFormSelector = formValueSelector('newRelationForm');

CreateRelationForm = connect(
    state => {
        return {
            sourceDatabaseNameValue: relationFormSelector(state, 'sourceDatabaseName'),
            sourceDatasetNameValue: relationFormSelector(state, 'sourceDatasetName'),
            sourceEntityTypeValue: relationFormSelector(state, 'sourceEntityType'),
            sourceEntityIdValues: relationFormSelector(state, 'sourceEntityId'),
            destinationDatabaseNameValue: relationFormSelector(state, 'destinationDatabaseName'),
            destinationDatasetNameValue: relationFormSelector(state, 'destinationDatasetName'),
            destinationEntityTypeValue: relationFormSelector(state, 'destinationEntityType'),
            destinationEntityIdValues: relationFormSelector(state, 'destinationEntityId'),
            relationNameValues: relationFormSelector(state, 'relationName')
        }
    }
)(CreateRelationForm);