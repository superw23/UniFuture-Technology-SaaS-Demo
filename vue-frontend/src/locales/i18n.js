// Made by GRPTeam202205
import {createI18n} from 'vue-i18n';
import en from './en.json';
import ch from './ch.json';

const i18n = createI18n({
    locale: "en",
    messages: {
        en,
        ch
    }
});

export default i18n;