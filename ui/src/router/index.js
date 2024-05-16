import {createRouter, createWebHistory} from "vue-router";
import HotelsView from "@/views/hotels/HotelsView.vue";
import HotelDetails from "@/views/hotels/HotelDetails.vue";
import LoginView from "@/views/authentication/LoginView.vue";
import RegisterView from "@/views/authentication/RegisterView.vue";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: "/",
            name: "Home",
            component: HotelsView
        },
        {
            path: "/:id",
            name: "HotelDetails",
            component: HotelDetails
        },
        {
            path: "/login",
            name: "Login",
            component: LoginView
        },
        {
            path: "/register",
            name: "Register",
            component: RegisterView
        },
        {
            path: "/:pathMatch(.*)*",
            redirect: "/"
        }
    ]
});

export default router;
