import Vue from 'vue';
import Router from 'vue-router';
import HomePage from '@/components/HomePage.vue';
import ProjectList from '@/components/projects/ProjectList.vue';
import ProjectsForm from '@/components/projects/ProjectsForm.vue';
import ModelsIndex from '@/components/models/ModelsIndex.vue';
import ModelForm from '@/components/models/ModelForm.vue';
import DeveloperChat from '@/components/developer/DeveloperChat.vue';
import ScheduledTaskIndex from '@/components/scheduledTasks/ScheduledTaskIndex.vue';
import ScheduledTaskEdit from '@/components/scheduledTasks/ScheduledTaskEdit.vue';
import TableMetadataIndex from '@/components/tablemetadata/TableMetadataIndex.vue';
import TableMetadataEdit from '@/components/tablemetadata/TableMetadataEdit.vue';
import NotificationRecipientIndex from '@/components/recipients/NotificationRecipientIndex.vue';
import NotificationLogIndex from '@/components/notifications/NotificationLogIndex.vue';
import ProjectDetails from '@/components/projects/ProjectDetails.vue';
import TasksIndex from '@/components/tasks/TasksIndex.vue';


Vue.use(Router);

const routes = [
    {
        path: '/',
        name: 'HomePage',
        component: HomePage
    },
    {
        path: '/developer',
        name: 'DeveloperChat',
        component: DeveloperChat
    },
    {
        path: '/projects',
        name: 'ProjectList',
        component: ProjectList
    },
    {
        path: '/projects/create',
        name: 'ProjectForm',
        component: ProjectsForm,
        props: true
    },
    {
        path: '/projects/:projectId/details',
        name: 'ProjectDetails',
        component: ProjectDetails
    },
    {
        path: '/models',
        name: 'ModelIndex',
        component: ModelsIndex
    },
    {
        path: '/models/create',
        name: 'ModelForm',
        component: ModelForm,
        props: true
    },
    {
        path: '/tasks',
        name: 'TasksIndex',
        component: TasksIndex
    },
    {
        path: '/services',
        name: 'ScheduledTaskIndex',
        component: ScheduledTaskIndex
    },
    {
        path: '/services/:serviceName',
        name: 'ScheduledTaskEdit',
        component: ScheduledTaskEdit,
        props: true
    },
    {
        path: '/tables',
        name: 'TableMetadataIndex',
        component: TableMetadataIndex
    },
    {
        path: '/tables/:id',
        name: 'TableMetadataEdit',
        component: TableMetadataEdit,
        props: true
    },
    {
        path: '/recipients',
        name: 'NotificationRecipientIndex',
        component: NotificationRecipientIndex
    },
    {
        path: '/notificationlogs',
        name: 'NotificationLogIndex',
        component: NotificationLogIndex
    }

];

const router = new Router({
    mode: 'history',
    routes
});

export default router;
