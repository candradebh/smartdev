import Vue from 'vue';
import Router from 'vue-router';
import HomePage from '@/components/HomePage.vue';
import Login from '@/components/auth/Login.vue';
import Register from '@/components/user/Register.vue';
import Profile from '@/components/user/Profile.vue';
import Settings from '@/components/user/Settings.vue';
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
        component: HomePage,
        meta: {requiresAuth: true}
    },
    {
        path: '/login',
        name: 'LoginPage',
        component: Login,
        meta: {requiresAuth: false, hideNavbar: true}
    },
    {
        path: '/register',
        name: 'RegisterPage',
        component: Register,
        meta: {requiresAuth: false, hideNavbar: true}
    },
    {
        path: '/profile',
        name: 'Profile',
        component: Profile,
        meta: {requiresAuth: true}
    },
    {
        path: '/settings',
        name: 'SettingPage',
        component: Settings,
        meta: {requiresAuth: true}
    },
    {
        path: '/developer',
        name: 'DeveloperChat',
        component: DeveloperChat,
        meta: {requiresAuth: false, hideNavbar: true}
    },
    {
        path: '/projects',
        name: 'ProjectList',
        component: ProjectList,
        meta: {requiresAuth: false, hideNavbar: true}
    },
    {
        path: '/projects/create',
        name: 'ProjectForm',
        component: ProjectsForm,
        props: true,
        meta: {requiresAuth: false, hideNavbar: true}
    },
    {
        path: '/projects/:projectId/details',
        name: 'ProjectDetails',
        component: ProjectDetails,
        meta: {requiresAuth: false, hideNavbar: true}
    },
    {
        path: '/models',
        name: 'ModelIndex',
        component: ModelsIndex,
        meta: {requiresAuth: false, hideNavbar: true}
    },
    {
        path: '/models/create',
        name: 'ModelForm',
        component: ModelForm,
        props: true,
        meta: {requiresAuth: false, hideNavbar: true}
    },
    {
        path: '/tasks',
        name: 'TasksIndex',
        component: TasksIndex,
        meta: {requiresAuth: false, hideNavbar: true}
    },
    {
        path: '/services',
        name: 'ScheduledTaskIndex',
        component: ScheduledTaskIndex,
        meta: {requiresAuth: false, hideNavbar: true}
    },
    {
        path: '/services/:serviceName',
        name: 'ScheduledTaskEdit',
        component: ScheduledTaskEdit,
        props: true,
        meta: {requiresAuth: false, hideNavbar: true}
    },
    {
        path: '/tables',
        name: 'TableMetadataIndex',
        component: TableMetadataIndex,
        meta: {requiresAuth: false, hideNavbar: true}
    },
    {
        path: '/tables/:id',
        name: 'TableMetadataEdit',
        component: TableMetadataEdit,
        props: true,
        meta: {requiresAuth: false, hideNavbar: true}
    },
    {
        path: '/recipients',
        name: 'NotificationRecipientIndex',
        component: NotificationRecipientIndex,
        meta: {requiresAuth: false, hideNavbar: true}
    },
    {
        path: '/notificationlogs',
        name: 'NotificationLogIndex',
        component: NotificationLogIndex,
        meta: {requiresAuth: false, hideNavbar: true}
    }

];

const router = new Router({
    mode: 'history',
    routes
});

router.beforeEach((to, from, next) => {
    const token = localStorage.getItem('token');
    if (to.matched.some(record => record.meta.requiresAuth) && !token) {
        next('/login');
    } else {
        next();
    }
});

export default router;
