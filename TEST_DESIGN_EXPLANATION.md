# Test Design Explanation

## API (Swagger Petstore)

### Approach

All API test scenarios were derived directly from the [Swagger Petstore documentation](https://petstore.swagger.io/), using the documented endpoints, request/response models, and field constraints (types, enums) as the source of truth for both positive and negative test design.

### Rationale for implemented scenarios

| Scenario | Rationale |
|---|---|
| **Positive test – create, update, get and delete a pet** | Exercises the full CRUD lifecycle (`POST /pet`, `PUT /pet`, `GET /pet/{petId}`, `DELETE /pet/{petId}`) as defined in Swagger, verifying both endpoint correctness and data consistency across the same pet record end-to-end. This is the highest business-value "happy path" and was prioritised first. |
| **JSON schema validation for POST /pet response** | Validates the response structure against the Pet model defined in Swagger's **Models** section (`id`, `category`, `name`, `photoUrls`, `tags`, `status`, including field types). This is a contract test, ensuring the API honours its documented schema rather than only checking status codes/values. |
| **Negative test – invalid pet status value** | Swagger constrains `status` to `enum: [available, pending, sold]`. Sending a value outside this enum (`NonExist`) verifies that server-side enum validation is enforced, not just documented. |
| **Negative test – invalid string number pet id** | Swagger defines `id` as `integer($int64)`. Sending a string value (`"abcde"`) verifies the API's type validation and resilience to malformed/unexpected input types. |

**Prioritisation:** the positive CRUD flow and schema validation are **High** priority (core functionality and contract guarantee); the two negative scenarios are **Medium** priority (input validation, robustness).

### Design note: single scenario covering multiple CRUD steps

`Crud_PetStoret.feature`'s main scenario chains POST → PUT → GET → DELETE → GET within one `Scenario` rather than splitting each HTTP method into its own scenario.

- **Why:** the steps are inherently sequential and state-dependent — PUT/GET/DELETE all operate on the `petId` returned by the initial POST. Splitting them into independent scenarios would require additional setup (fixed test data, hooks, or context injection) to recreate that dependency, adding complexity without extra coverage.
- **Alternatives considered:**
  - *One scenario per HTTP method*, each seeding its own known pet ID via a shared `Background`/test data builder — improves isolation and parallel-run safety, but no longer validates a real end-to-end lifecycle in a single run.
  - *Scenario Outline + Examples* (the pattern used on the UI side, e.g. `Login.feature`) — well suited to data-driven variations of the same steps, but not to a sequence of dependent, state-changing steps like this.
- **Trade-off accepted:** the current approach favours realistic lifecycle coverage over strict scenario isolation; negative cases were deliberately kept as separate, independent scenarios since they don't depend on prior state.

### Note on payload construction (`jsonPayload.java`)

Payloads are currently defined as static `String` literals. This is acceptable for the current scope, but doesn't scale well as the number of payload variations grows. A future improvement would be to introduce a POJO/DTO (e.g. `Pet.java`) and serialise it with Jackson/Gson, giving type-safe, field-overridable payload construction (e.g. `Pet.builder().status("sold").build()`) instead of duplicated string blocks.

### Further API scenarios (outlined, not automated)

| Scenario | Type | Approach | Rationale | Priority |
|---|---|---|---|---|
| POST `/pet` with missing required fields (`name`, `photoUrls`) | Negative | Send payload omitting required fields per schema; assert 4xx status | Swagger marks `name` and `photoUrls` as required; verifies mandatory-field enforcement | High |
| GET `/pet/{petId}` with non-existent ID | Negative | Request an ID guaranteed not to exist; assert 404 and error body shape | Common real-world case (stale link, deleted resource); already partially covered by the re-GET-after-delete step, worth an explicit standalone case | High |
| GET `/pet/{petId}` with negative or zero ID | Negative | Send `-1` / `0` as petId; assert appropriate error handling | Boundary/edge-case input for an `int64` field; checks the API doesn't silently accept invalid ranges | Medium |
| PUT `/pet` for a pet ID that doesn't exist | Negative | Attempt update on non-existent resource; assert 404 | Verifies update operations don't succeed on non-existent state (data integrity) | Medium |
| POST `/pet` with an extremely large `id` (beyond int64 range) | Negative | Send an oversized numeric string; assert 4xx / graceful handling | Boundary test for the `int64` format constraint | Low |
| GET `/pet/findByStatus?status=available` | Positive | Query pets by valid enum status; assert all returned items have that status | Validates a commonly used filter endpoint beyond basic CRUD | Medium |
| GET `/pet/findByStatus` with invalid status value | Negative | Query with a value outside the enum; assert error/empty response as documented | Extends the enum-validation coverage already applied to POST, onto a GET/query endpoint | Low |
| POST `/pet` with malformed JSON body (broken syntax) | Negative | Send invalid JSON string as body; assert 400 | Ensures the API fails gracefully on unparsable input, not just semantically invalid input | Medium |
| DELETE `/pet/{petId}` twice in a row (idempotency check) | Negative | Delete the same pet twice; assert first succeeds (200), second returns 404 | Verifies DELETE isn't silently idempotent in a way that hides errors, matching documented behaviour | Low |

---

## UI (SauceDemo)

### Approach

All UI scenarios target [SauceDemo](https://www.saucedemo.com/) and are built around its documented set of test user accounts (`standard_user`, `locked_out_user`, `problem_user`, `performance_glitch_user`, `error_user`, `visual_user`), each of which is designed by SauceDemo to trigger a specific behaviour (successful login, blocked login, visual/data bugs, slow rendering, partial functional errors). Test design is driven by exercising each account against the relevant part of the purchase journey (login → product browsing → cart → checkout → confirmation), rather than testing every step with every account.

### Rationale for implemented scenarios

| Scenario | Rationale |
|---|---|
| **`Login.feature` – verify that users can access the main page** (data-driven via Scenario Outline/Examples across all 6 SauceDemo user types + an empty-credentials case) | Covers both positive (each valid user type reaches the Products page) and negative (`locked_out_user` blocked with an error message, empty username/password rejected) login outcomes in one reusable flow. Using all documented user types ensures each account's distinct behaviour is explicitly asserted rather than assumed. |
| **`Product.feature` – different product pictures (`problem_user`)** | `problem_user` is SauceDemo's documented "broken UI/data" account. This scenario checks for the known duplicate/broken product image behaviour, acting as a regression check that this documented defect state is still reproducible and detected — useful for confirming the test suite can catch UI-level bugs, not just functional ones. |
| **`Product.feature` – add to cart without restriction (`error_user`)** | `error_user` is documented to behave inconsistently on certain actions. Verifying that "Add to cart" still succeeds (button flips to "Remove") for this account checks that partial/expected error behaviour doesn't block the core add-to-cart action, distinguishing genuine functional breakage from the account's known quirks. |
| **`ProductCheckOutJourney.feature` – full checkout journey (`standard_user`)** | End-to-end smoke test (`@smokeTest`) covering the highest business-value path: login → add multiple products → cart → checkout details → order overview (with price total) → order confirmation → return to Products page. `standard_user` was chosen for this scenario specifically because it has no known account-level quirks, so any failure here reliably indicates a real regression in the checkout flow itself rather than account-specific behaviour. |

**Prioritisation:** the full checkout journey and core login flow are **High** priority (primary business flows); the `problem_user`/`error_user` checks are **Medium** priority (account-specific edge behaviour, valuable but secondary to core flow correctness).

### Design note: other user types commented out in the checkout journey

`ProductCheckOutJourney.feature` lists the other five user types as commented-out `Examples` rows rather than active cases. This was a deliberate scope decision: running the full checkout flow against accounts with known UI/data quirks (`problem_user`, `visual_user`) would require scenario-specific assertions to distinguish "expected known quirk" from "genuine failure," which was out of scope for this exercise. They're left commented to signal intended future coverage rather than an oversight.

### Further UI scenarios (outlined, not automated)

| Scenario | Type | Approach | Rationale | Priority |
|---|---|---|---|---|
| Checkout with one or more required fields left empty (First Name / Last Name / Zip) | Negative | Submit the Checkout: Your Information form with a field blank; assert the SauceDemo inline error message for that field | Validates client-side required-field enforcement on the checkout form, a common real-world failure point | High |
| Attempt to reach Checkout: Overview / Complete pages directly via URL without completing prior steps | Negative | Navigate directly to a later checkout step URL while cart/session state is incomplete; assert user is redirected or blocked | Tests the app doesn't allow skipping required steps in an unexpected state | Medium |
| Remove a product from "Your Cart" page before checkout | Positive | Click "Remove" on a cart item; assert cart badge count and item list update accordingly | Common real user action; verifies cart state consistency outside the happy-path add flow | Medium |
| "Cancel" button on Checkout: Your Information and Checkout: Overview pages | Positive | Click "Cancel"; assert user is returned to Cart / Products page respectively without completing the order | Verifies users can safely back out of checkout without unintended side effects (e.g. order still placed) | Medium |
| Sort products (Name A–Z/Z–A, Price low–high/high–low) | Positive | Select each sort option from the dropdown; assert product order matches expected sort | Commonly used feature not currently covered; low complexity, clear pass/fail criteria | Low |
| Logout via the hamburger menu | Positive | Click menu → Logout; assert redirect to the login page and that session/cart state doesn't persist | Verifies session termination behaves correctly, relevant for security/data-isolation between test runs | Medium |
| `performance_glitch_user` full journey with explicit wait/timeout assertions | Positive | Run the core login/checkout flow with this account; assert functional correctness despite the deliberately slower page loads | Ensures test automation and the app itself remain functionally correct under degraded performance, not just under ideal timing | Low |
| Add an out-of-stock/removed product reference (e.g. stale product ID) to cart | Negative | Attempt to reference a product that no longer exists in the current inventory listing; assert graceful handling | Simulates an unexpected/stale state (e.g. session or catalog change mid-journey) | Low |
| `visual_user` visual regression check | Positive | Capture and compare screenshots of key pages (Products, Cart) for `visual_user` against a baseline | `visual_user` exists specifically to test visual regressions; not automated here as it requires a visual-diff tool not currently in the framework | Low |
