import {rest} from 'msw'
import {alert} from '../modelMocks.js';

export const alertHandler = [
    rest.get("/alerts", (req, res, ctx) => {
        return res(
            // Respond with a 200 status code
            ctx.status(200),
            ctx.body(alert)
        )
    }),

    // rest.get("/lists/1", (req, res, ctx) => {
    //     return res(
    //         // Respond with a 200 status code
    //         ctx.status(200),
    //         ctx.body(list)
    //     )
    // })
]

export default alertHandler